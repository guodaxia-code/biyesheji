package xyz.worldzhile.swagger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class MySwaggerConfig {
    @Bean
    public Docket myDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("至乐购商城[Api接口文档]") // 标题
                .description("毕业设计购物商城") // 描述
                .contact(new Contact("郭健全", "http://www.baidu.com", "1154424892@qq.com")) // 联系方式
                .version("1.0") // 版本号
                .build();
        docket.apiInfo(apiInfo);
        //设置只生成被Api这个注解注解过的Ctrl类中有ApiOperation注解的api接口的文档
        docket.select().apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build();
        return docket;
    }
}


/*@Api：用在请求的类上，表示对类的说明
        tags="说明该类的作用，可以在UI界面上看到的注解"
        value="该参数没什么意义，在UI界面上也看到，所以不需要配置"

@ApiOperation：用在请求的方法上，说明方法的用途、作用
        value="说明方法的用途、作用"
        notes="方法的备注说明"

@ApiImplicitParams：用在请求的方法上，表示一组参数说明
@ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
        name：参数名
        value：参数的汉字说明、解释
        required：参数是否必须传
        paramType：参数放在哪个地方
        · header --> 请求参数的获取：@RequestHeader
            · query --> 请求参数的获取：@RequestParam
            · path（用于restful接口）--> 请求参数的获取：@PathVariable
            · body（不常用）
                    · form（不常用）
                    dataType：参数类型，默认String，其它值dataType="Integer"
                    defaultValue：参数的默认值

@ApiResponses：用在请求的方法上，表示一组响应
@ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
        code：数字，例如400
        message：信息，例如"请求参数没填好"
        response：抛出异常的类

@ApiModel：用于响应类上，表示一个返回响应数据的信息
        （这种一般用在post创建的时候，使用@RequestBody这样的场景，
        请求参数无法使用@ApiImplicitParam注解进行描述的时候）
@ApiModelProperty：用在属性上，描述响应类的属性*/
