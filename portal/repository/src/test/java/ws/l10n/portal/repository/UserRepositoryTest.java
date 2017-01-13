package ws.l10n.portal.repository;

import org.junit.Ignore;
import org.junit.Test;
import ws.l10n.portal.domain.PortalUser;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(value = {"classpath:test-h2-l10n-persistence.xml"})
//@Transactional
public class UserRepositoryTest {

//    @Autowired
//    private UserRepository userRepository;

    @Test
    @Ignore
    public void testSave() throws Exception {
//        User user = createUser();
//        userRepository.save(user);
//        User result = userRepository.get(user.getId());
//
//        assertEquals(user.getId(), result.getId());
//        assertEquals(user.getEmail(), result.getEmail());
//        assertEquals(user.getPassword(), result.getPassword());

    }

    private PortalUser createUser() {
//        return new User(null, UUID.randomUUID().toString(),UUID.randomUUID().toString());
        return null;
    }

}
