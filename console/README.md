
### 

这个项目可以运行在两种模式下，这两种模式有着很大的不同

第一种模式是开发模式：
在这种模式下，前端的Angular是运行在单独的Web容器中，例如 Angular Server 或者是 Nginx，apache。而后台的API是运行在spring boot 内置
Tomcat中。前后端分离。

一般的操作手法为：

先启动 spring boot 的应用，这时候后端的服务运行在8081端口

通过在终端运行 npm run start 启动 angular server, 这个我们可以看看 frontend 目录下的
package.json, npm run start 实际上运行的是 ng serve --proxyConfig proxy-conf.json
注意 --proxyConfig 选项，通过这个选项我们可以引入一个代理配置文件。查看proxy-conf.json
文件，我们发现所有访问 /api/** 都会被转发到 http://localhost:8081，这个正是我们的服务端
spring boot 程序启动后的端口。

前端运行在4200端口，后端服务运行在8081端口，这时候前端访问后端是通过跨域访问，所以后端
需要允许跨域访问。


第二种方式是部署模式：
通过运行 npm run build 命令，那么angular的前端程序会编译打包到spring boot 项目下的
resources/static目录下，那么所有在这个目录下的文件都会被当做静态资源被访问到。我们知道一般
的angular项目都是单入口的应用（不是单页面），入口文件为index.html。这时候会出现一个问题
当我们访问 http://localhost:8081/categories这样的路径的时候，因为static目录下并不会有
categories这样的文件，而且如果刚好我们spring boot mvc也没有配置相应的请求映射（request 
mapping), 那么就会出错。我们为了让这样的一些路径也可以被angular处理，我们需要在spring boot
项目中进行配置，将请求引到 static/index.html进行处理

    package com.beijiake.web.config;
    
    import org.springframework.context.annotation.Configuration;
    import org.springframework.core.io.ClassPathResource;
    import org.springframework.core.io.Resource;
    import org.springframework.web.servlet.config.annotation.CorsRegistry;
    import org.springframework.web.servlet.config.annotation.EnableWebMvc;
    import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
    import org.springframework.web.servlet.resource.PathResourceResolver;
    
    import java.io.IOException;
    
    @Configuration
    //@EnableWebMvc  //disable this annotation for serve the static resources
    public class WebConfig implements WebMvcConfigurer {
    
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/**");
        }
    
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**/*")
                    .addResourceLocations("classpath:/static/")
                    .resourceChain(true)
                    .addResolver(new PathResourceResolver() {
                        @Override
                        protected Resource getResource(String resourcePath,
                                                       Resource location) throws IOException {
                            Resource requestedResource = location.createRelative(resourcePath);
                            return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
                                    : new ClassPathResource("/static/index.html");
                        }
                    });
        }
    }
 
这样一来，所有的事情就搞定，只要spring boot 没有进行请求映射的都转到angular来处理。
这时候angular就要定义自己的 404 错误页面。

在这种模式下只要启动 spring boot 服务即可。angular前端和spring boot 后端都是运行
在8081端口下。那么访问 /api/** 这样的后端服务也相应成为同域访问。


--proxyConfig的精妙之处在于我们无需在开发模式和部署模式之间对配置进行任何改动，就能
让两种模式都正常运作。
 


#### 关于分页以及排序的写法


http://{serverAddress}:{port}/api/products?page=0&size=20&sort=name,gender,asc&sort=age,desc

特别在多个字段的查询


### @EnableWebMvc 打开的的时候静态资源无法被访问的解决方法

https://stackoverflow.com/questions/24661289/spring-boot-not-serving-static-content


### Angular2 在 spring boot 的路由问题

当我们只是访问 http://localhost:8082/这样的路径的时候，spring boot 能够使用resources/static/index.html文件来响应，但是如果我们
访问类似如下的路径 http://localhost:8082/about这样的地址的时候，因为没有实际的about文件存在，所以需要我们把这样的路径交给
resources/static/index.html处理，那么我们需要进行必要的设置

https://stackoverflow.com/questions/38516667/springboot-angular2-how-to-handle-html5-urls