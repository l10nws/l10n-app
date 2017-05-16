package ws.l10n.portal.repository;

import org.junit.Ignore;
import org.junit.Test;
import ws.l10n.portal.domain.Project;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(value = {"classpath:test-h2-l10n-persistence.xml"})
//@Transactional
public class ProjectRepositoryTest {

//    @Autowired
//    private ProjectRepository projectRepository;

    @Test
    @Ignore
    public void testSave() throws Exception {
//        Project project = createProject(orgId);
//        Integer id = projectRepository.insert(project);
//        List<Project> result = projectRepository.getByOrgId(orgId);
//        assertEquals(1, result.size());
//        assertEquals(id, result.get(0).getId());
//        assertEquals(project.getOrgId(), result.get(0).getOrgId());
//        assertEquals(project.getName(), result.get(0).getName());
    }

    private Project createProject(Integer orgId) {
//        return new Project(null, orgId, UUID.randomUUID().toString());
        return null;
    }

}