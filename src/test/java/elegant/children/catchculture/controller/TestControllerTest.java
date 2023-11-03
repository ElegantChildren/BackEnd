package elegant.children.catchculture.controller;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@WebMvcTest(controllers = TestController.class)
class TestControllerTest {

//        @Autowired
//        private MockMvc mockMvc;
//
//        @MockBean
//        private UserRepository userRepository;
//
//        @BeforeEach
//        void setUp(WebApplicationContext applicationContext) {
//                mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
//                                .build();
//        }
//
//        @Test
//        @WithCustomMockUser
//        public void test1() throws Exception {
//
//                doReturn(Optional.of(User.builder()
//                        .id(1)
//                        .email("test")
//                        .build())).when(userRepository).findByEmail(any(String.class));
//                MvcResult mvcResult = mockMvc.perform(
//                        MockMvcRequestBuilders.get("/test")
//                ).andExpect(status().isOk()).andReturn();
//                String contentAsString = mvcResult.getResponse().getContentAsString();
//                System.out.println(contentAsString);
//        }
//
//        @Test
//        public void test2() throws Exception {
//                doReturn(Optional.empty()).when(userRepository).findByEmail(any(String.class));
//
//                MvcResult mvcResult = mockMvc.perform(
//                        MockMvcRequestBuilders.get("/test")
//                ).andExpect(status().isOk()).andReturn();
//
//                String contentAsString = mvcResult.getResponse().getContentAsString();
//                System.out.println(contentAsString);
//        }

}