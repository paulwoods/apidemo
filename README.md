# apidemo
A repo used to help teach api design and swagger/openapi integration.

## Run

gradle bootRun

## Exposed swagger endpoints

* The generated swagger file:
http://localhost:8080/v2/api-docs

* The generated swagger ui:
http://localhost:8080/swagger-ui.html

## Adding Swagger

Two add swagger to an existing project do these:
1. add the dependencies
    * compile "io.springfox:springfox-swagger2:2.9.2"
    * compile 'io.springfox:springfox-swagger-ui:2.9.2'

1. Then add a bean to your configuration (in the ApidemoApplication.groovy file)

<code>

    @Bean
    Docket myApi() {
        new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(MetaClass.class)
    }

</code>

