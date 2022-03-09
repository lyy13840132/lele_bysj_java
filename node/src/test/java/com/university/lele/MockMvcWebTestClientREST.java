package com.university.lele;

import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.restdocs.templates.TemplateFormats;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @ClassName: MockMvcWebTestClientREST
 * @BelongPackage: com.yiyunnet.node
 * @Description:
 * @Author: HTB
 * @Date: 2021/11/22 上午11:10
 * @Version: V2.1.0
 */
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class MockMvcWebTestClientREST {

    public MockMvc mockMvc;

//    @RegisterExtension
//    final RestDocumentationExtension restDocumentation = new RestDocumentationExtension ("custom");

//    @Autowired
//    private WebApplicationContext context;

//    @BeforeEach
//    public void setUp(){
//        //默认生成的文档片段
//        Snippet[] defaultSnippets = new Snippet[]{CliDocumentation.curlRequest(), CliDocumentation.httpieRequest(), HttpDocumentation.httpRequest(), HttpDocumentation.httpResponse(), PayloadDocumentation.requestBody(), PayloadDocumentation.responseBody()};
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
//                .apply(documentationConfiguration(this.restDocumentation)
//                        //此处也支持生成markdown文档片段，但不能生成html
//                        .snippets().withTemplateFormat(TemplateFormats.asciidoctor()).withEncoding("UTF-8")
//                        .withDefaults(defaultSnippets)
//                        .and()
//                        .uris().withScheme("https").withHost("LOCALHOST").withPort(443)
//                        .and()
//                )
//                .build();
//    }

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void testHello() throws Exception {
       this.mockMvc.perform(RestDocumentationRequestBuilders.get("/hello")
                .param("name" , "HTB")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("msg" , "HRLLO SSSSS").exists())
                .andDo(document("hello"));
    }



}
