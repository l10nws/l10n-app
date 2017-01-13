package ws.l10n.portal.ui.mvc;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ws.l10n.portal.domain.Message;
import ws.l10n.portal.domain.MessageBundle;
import ws.l10n.portal.domain.Version;
import ws.l10n.portal.repository.BreadcrumbRepository;
import ws.l10n.portal.repository.BundleRepository;
import ws.l10n.portal.repository.LocaleRepository;
import ws.l10n.portal.repository.MessageRepository;
import ws.l10n.portal.repository.VersionRepository;
import ws.l10n.portal.service.LocaleService;
import ws.l10n.portal.service.MessageService;
import ws.l10n.portal.service.VersionService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.Assert.state;


/**
 * @author Sergey Boguckiy
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private BreadcrumbRepository breadcrumbRepository;
    @Autowired
    private LocaleRepository localeRepository;
    @Autowired
    private LocaleService localeService;
    @Autowired
    private BundleRepository bundleRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private VersionRepository versionRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private VersionService versionService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canWriteVersion(#versionId)")
    public String createForAllLocales(@RequestParam Integer versionId,
                                      @RequestParam String selectedLocaleId,
                                      @RequestParam String key,
                                      @RequestParam("localeId") String[] localeIds,
                                      @RequestParam("value") String[] values) {
        state(localeIds.length == values.length);

        List<Message> messages = new ArrayList<Message>();
        int i = 0;
        for (String localeId : localeIds) {
            messages.add(new Message(null, versionId, key, values[i++], localeId));
        }
        messageService.addToAllLocales(versionId, key, messages);
        return "redirect:/message?v=" + versionId + "&l=" + selectedLocaleId;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canWriteVersion(#versionId)")
    public String update(@RequestParam Integer versionId,
                         @RequestParam String selectedLocaleId,
                         @RequestParam String key,
                         @RequestParam("localeId") String[] localeIds,
                         @RequestParam("messageId") Integer[] messageIds,
                         @RequestParam("value") String[] values) {
        state(localeIds.length == values.length);
        state(values.length == messageIds.length);

        List<Message> messages = new ArrayList<Message>();
        int i = 0;
        for (String localeId : localeIds) {
            messages.add(new Message(messageIds[i], versionId, key, values[i++], localeId));
        }
        messageService.updateForAllLocales(versionId, key, messages);
        return "redirect:/message?v=" + versionId + "&l=" + selectedLocaleId;
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canWriteVersion(#versionId)")
    public String saveAll(@RequestParam("localeId") String localeId,
                          @RequestParam("versionId") Integer versionId,
                          @RequestParam(value = "key", required = false) String[] keys,
                          @RequestParam(value = "value", required = false) String[] values) {
        if (keys == null || values == null) {
            return "redirect:/message?v=" + versionId + "&l=" + localeId;
        } else if (keys.length != values.length) {
            throw new RuntimeException();
        }
        int i = 0;
        List<Message> messages = new ArrayList<Message>();
        for (String key : keys) {
            messages.add(new Message(null, versionId, key, values[i++], localeId));
        }
        messageRepository.saveAll(messages);
        return "redirect:/message?v=" + versionId + "&l=" + localeId;
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canReadVersion(#versionId)")
    @ResponseBody
    public Map<String, Message> getByVersionIdAndKey(@RequestParam("key") String key,
                                                     @RequestParam("versionId") Integer versionId) {
        Map<String, Message> result = Maps.uniqueIndex(messageRepository.findByVersionIdAndKey(versionId, key),
                new Function<Message, String>() {
                    @Override
                    public String apply(Message input) {
                        return input.getLocaleId();
                    }
                });
        return new HashMap<String, Message>(result);
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canReadVersion(#versionId)")
    public String messagesByBundle(@RequestParam("v") Integer versionId,
                                   @RequestParam(value = "l", required = false) String localeId,
                                   ModelMap map) {
        versionRepository.updateViewDate(versionId);


        Version version = versionRepository.get(versionId);
        MessageBundle messageBundle = bundleRepository.get(version.getMessageBundleId());

        if (StringUtils.isEmpty(localeId)) {
            localeId = version.getDefaultLocaleId();
        }

        map.put("createdLocales", localeRepository.getLocalesByVersionId(versionId));

        map.put("locales", localeRepository.getLocaleViews());
        map.put("versionId", versionId);
        map.put("bundleLabel", messageBundle.getLabel());
        map.put("messages", messageRepository.findByVersionIdAndLocaleId(versionId, localeId));
        map.put("selectedLocaleId", localeId);

        map.put("breadcrumbs", breadcrumbRepository.getMessagesBreadcrumb(versionId));

        map.put("defaultLocale", localeRepository.getLocaleViewById(version.getDefaultLocaleId()));

        return "messages";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canWriteVersion(#versionId)")
    @ResponseBody
    public void delete(@RequestParam("versionId") Integer versionId,
                       @RequestParam("localeId") String localeId,
                       @RequestParam("keys[]") List<String> keys,
                       @RequestParam("deleteFromAllLocales") Boolean deleteFromAllLocales) {
        if (deleteFromAllLocales) {
            messageRepository.deleteAll(versionId, keys);
        } else {
            messageRepository.deleteAllByLocaleId(versionId, localeId, keys);
        }
    }

    @RequestMapping(value = "/locale/add", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canWriteVersion(#versionId)")
    public String addLocale(@RequestParam Integer versionId, @RequestParam String localeId, @RequestParam(defaultValue = "false") boolean shouldCopy) {
        localeService.addLocale(versionId, localeId, shouldCopy);
        return "redirect:/message?v=" + versionId + "&l=" + localeId;
    }

    @RequestMapping(value = "/locale/delete", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canWriteVersion(#versionId)")
    public String deleteLocale(@RequestParam("versionId") Integer versionId,
                               @RequestParam("localeId") String localeId) {
        versionService.deleteLocale(versionId, localeId);
        return "redirect:/message?v=" + versionId;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canWriteVersion(#versionId)")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam(defaultValue = "false") boolean replaceExisted,
                             @RequestParam Integer versionId,
                             @RequestParam String localeId) throws IOException {
        if (!file.isEmpty()) {
            messageService.parseAndSave(file.getInputStream(), localeId, versionId, replaceExisted);
        }
        return "redirect:/message?v=" + versionId + "&l=" + localeId;
    }


    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canReadVersion(#versionId)")
    public void downloadFile(HttpServletResponse response,
                           @RequestParam Integer versionId,
                           @RequestParam String localeId) throws IOException {
        response.setContentType("text/plain");
        String fileName = "messages_" + localeId;
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".properties");
        messageService.writeMessages(versionId, localeId, response.getWriter());
    }

    @RequestMapping(value = "/create/exist", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canReadVersion(#versionId)")
    @ResponseBody
    public void exist(@RequestParam("key") String key,
                      @RequestParam("versionId") Integer versionId,
                      HttpServletResponse response) {
        List<Message> messages = messageRepository.findByVersionIdAndKey(versionId, key);
        if (messages.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @RequestMapping(value = "/edit/exist", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canReadVersion(#versionId)")
    @ResponseBody
    public void exist(@RequestParam("key") String key,
                      @RequestParam("oldKey") String oldKey,
                      @RequestParam("versionId") Integer versionId,
                      @RequestParam("localeId") String localeId,
                      HttpServletResponse response) {
        Message message = messageRepository.find(key, localeId, versionId);
        if (message == null || message.getKey().equals(oldKey)) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

}
