package com.example.patient.config;



import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License; // 新增导入
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {

    /**
     * 接口信息
     * 注意：这是项目中唯一的 OpenAPI Bean
     */
    @Bean
    public OpenAPI openApi() {

        return new OpenAPI()
                // 将 OpenApiConfig 的 info 信息合并进来
                .info(new Info()
                        .title("Patient系统接口文档")
                        .description("API 接口文档") // 从 OpenApiConfig 合并
                        .version("1.0") // 从 OpenApiConfig 合并，建议统一版本号
                        .license(new License().name("Apache 2.0").url("http://springdoc.org/"))
                )

                // 保留您原有的安全组件配置
                .components(new Components()
                        .addSecuritySchemes(HttpHeaders.AUTHORIZATION,
                                new SecurityScheme()
                                        .name(HttpHeaders.AUTHORIZATION)
                                        .type(SecurityScheme.Type.HTTP)
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme("Bearer")
                                        .bearerFormat("JWT")
                        )
                );


    }

    /**
     * 全局自定义扩展
     * 在OpenAPI规范中，Operation 是一个表示 API 端点（Endpoint）或操作的对象。
     * 每个路径（Path）对象可以包含一个或多个 Operation 对象，用于描述与该路径相关联的不同 HTTP 方法（例如 GET、POST、PUT 等）。
     */
    @Bean
    public GlobalOpenApiCustomizer globalOpenApiCustomizer() {
        return openApi -> {
            // 全局添加鉴权参数
            if (openApi.getPaths() != null) {
                openApi.getPaths().forEach((s, pathItem) -> {
                    // 登录接口/验证码不需要添加鉴权参数
                    if ("/Patient".equals(s) || "/auth/captcha".equals(s)) {
                        return;
                    }
                    // 接口添加鉴权参数
                    pathItem.readOperations()
                            .forEach(operation ->
                                    operation.addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                            );
                });
            }
        };
    }
}
