# Consumer Service – REST Client Template with Eureka

[English](#english-version) | [فارسی](#نسخه-فارسی)

---

## English Version

### Overview

This project is a template microservice that demonstrates four different ways to consume a REST API in a Spring Boot application, all integrated with Eureka service discovery:

1. OpenFeign – Declarative REST client (Spring Cloud)
2. RestClient – Modern synchronous HTTP client (Spring Boot 3.2+)
3. WebClient – Reactive, non-blocking client (Spring WebFlux)
4. RestTemplate – Legacy synchronous client (maintained for backward compatibility)

Additionally, it showcases the HTTP Interface feature (@HttpExchange) which allows you to define a client contract once and use it with any of the above implementations via adapters.

Key Feature: This template includes a working solution for the critical RestClient + Eureka integration issue, where Eureka would incorrectly pick up a load-balanced RestClient.Builder and fail with "No instances available for localhost". The fix separates builders: a plain @Primary builder for Eureka's internal use, and a @LoadBalanced builder for application code.

This template is ideal for teams who want to evaluate different approaches or provide a unified example for their developers.

---

### Project Structure

```
consumer/
├── src/main/java/com/example/consumer/
│   ├── ConsumerApplication.java          # Main class
│   ├── feign/                            # Feign client implementation
│   │   ├── ProviderFeignClient.java      # Feign interface
│   │   └── FeignController.java          # REST endpoint
│   ├── restclient/                       # RestClient implementation
│   │   ├── RestClientConfig.java         # RestClient bean configuration (fix applied)
│   │   ├── ProviderRestClient.java       # Service using RestClient
│   │   └── RestClientController.java
│   ├── resttemplate/                     # RestTemplate implementation
│   │   ├── RestTemplateConfig.java       # Load-balanced RestTemplate
│   │   ├── RestTemplateClient.java       # Service using RestTemplate
│   │   └── RestTemplateController.java
│   ├── webclient/                        # WebClient implementation
│   │   ├── WebClientConfig.java          # Load-balanced WebClient
│   │   ├── ProviderWebClient.java        # Service using WebClient
│   │   └── WebClientController.java
│   └── httpinterface/                    # HTTP Interface with adapters
│       ├── ProviderHttpInterface.java    # Contract interface
│       ├── HttpInterfaceConfig.java      # Creates proxy beans (RestClient, RestTemplate)
│       └── HttpInterfaceController.java  # REST endpoint (uses @Primary bean)
└── src/main/resources/
└── application.yaml                  # Configuration (port, Eureka settings)
```

---

### Prerequisites

- Java 17+ (project uses Java 25, but works with 17+)
- Maven 3.8+
- Eureka Server running on port 8888 (provided in the same repository under /eureka – you need to start it first)
- Provider service running on port 8081 (provided in the same repository under /provider)

---

### How to Run

#### Step 1: Start the Eureka Server

```bash
cd eureka
mvn spring-boot:run
```

The Eureka dashboard will be available at http://localhost:8888.

#### Step 2: Start the Provider Service

```bash
cd provider
mvn spring-boot:run
```

The provider will register itself with Eureka and start on http://localhost:8081.

#### Step 3: Start the Consumer Service

```bash
cd consumer
mvn spring-boot:run
```

The consumer will register itself with Eureka and start on http://localhost:8080.

#### Step 4: Test the Endpoints

Once all services are running, you can call the following endpoints on the consumer:

| Client Type       | Endpoint                                      |
|-------------------|-----------------------------------------------|
| Feign             | GET http://localhost:8080/api/feign/instance  |
| RestClient        | GET http://localhost:8080/api/rest-client/instance |
| RestTemplate      | GET http://localhost:8080/api/rest-template/instance |
| WebClient         | GET http://localhost:8080/api/web-client/instance |
| Http Interface    | GET http://localhost:8080/api/http-interface/instance |

All endpoints return a string like:

```
Instance served by Port: 8081. Instance ID: 3b8f4c2a-...
```

---

### Detailed Explanation of Each Approach

#### 1. OpenFeign
- Declarative: Define an interface with Spring Cloud annotations.
- Service discovery: Uses Eureka to resolve the service name "provider".
- Integration: Built-in support for load balancing and resilience.
- Best for: Microservice-to-microservice communication in a Spring Cloud ecosystem.

#### 2. RestClient (Modern)
- Introduced in: Spring Boot 3.2 / Spring Framework 6.1.
- Synchronous: Blocking, but with a fluent and modern API.
- Load balancing: Works with Eureka via a dedicated load-balanced builder.
- Future-proof: Intended to replace RestTemplate.
- Best for: New projects that need a simple, synchronous HTTP client with service discovery.

#### 3. WebClient (Reactive)
- Reactive: Non-blocking, supports reactive streams (Mono/Flux).
- Flexible: Can be used both reactively and in a blocking way (with .block()).
- Load balancing: Uses LoadBalancerExchangeFilterFunction with Eureka.
- Best for: Reactive applications or when you need streaming/backpressure support.

#### 4. RestTemplate (Legacy)
- Legacy: Deprecated in favor of RestClient, but still supported.
- Synchronous: Blocking, familiar API for many developers.
- Load balancing: Can be made load-balanced with @LoadBalanced.
- Best for: Existing codebases that already use it; avoid for new projects.

#### 5. HTTP Interface (@HttpExchange)
- Unified contract: Write a single interface using @HttpExchange and generate proxies with adapters.
- Implementation-agnostic: Switch between RestClient, RestTemplate, or WebClient without changing the interface.
- Best for: Abstracting HTTP client details; especially useful when you want to support multiple clients.

---

### Configuration Highlights

#### RestClient + Eureka Fix

The critical issue where Eureka fails with "No instances available for localhost" is resolved by providing two separate RestClient.Builder beans:

```java
@Bean
@Primary
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public RestClient.Builder restClientBuilder(RestClientBuilderConfigurer configurer) {
return configurer.configure(RestClient.builder());
}

@Bean
@LoadBalanced
public RestClient.Builder loadBalancedRestClientBuilder(RestClientBuilderConfigurer configurer) {
return configurer.configure(RestClient.builder());
}
```

- The @Primary builder is used by Eureka's internal HTTP transport (plain, no load balancing).
- The @LoadBalanced builder is used by application code to call services via logical names (e.g., "http://provider").

Without this separation, Eureka tries to use the load-balanced builder, which attempts to resolve "localhost" as a service name and fails.

#### Using the Load-Balanced Builder

To create a RestClient with load balancing:

```java
@Bean
public RestClient restClient(@LoadBalanced RestClient.Builder builder) {
return builder
.baseUrl("http://provider") // Logical service name
.build();
}
```

The @LoadBalanced qualifier ensures that the correct builder is injected.

---

### Adding Customizations

Each client can be extended with:

- Timeouts (connection, read, write)
- Interceptors (logging, authentication)
- Error handling (custom exception mapping)
- Retry logic (using Spring Retry or resilience4j)

Refer to the official Spring documentation for each client to add these features.

---

### Technologies Used

- Spring Boot 4.1.1
- Spring Cloud 2025.1.3 (for OpenFeign and Eureka)
- Java 25 (compatible with 17+)
- Maven for build
- Lombok for reducing boilerplate

---

### License

This project is provided as a template – you are free to use and modify it for your own purposes.

---

## نسخه فارسی

### بررسی کلی

این پروژه یک تمپلیت برای میکروسرویس مصرف‌کننده است که چهار روش متفاوت برای فراخوانی REST API در اپلیکیشن Spring Boot، همگی با سرویس‌یابی Eureka، را نشان می‌دهد:

1. OpenFeign – کلاینت اعلانی (Spring Cloud)
2. RestClient – کلاینت همگام مدرن (Spring Boot 3.2+)
3. WebClient – کلاینت واکنش‌گرا و غیرهمگام (Spring WebFlux)
4. RestTemplate – کلاینت همگام قدیمی (برای سازگاری با کدهای قبلی)

علاوه بر این، قابلیت HTTP Interface (@HttpExchange) را نیز به نمایش می‌گذارد که به شما اجازه می‌دهد یک قرارداد کلاینت را یک بار تعریف کرده و با استفاده از آداپتورها، آن را با هر یک از پیاده‌سازی‌های بالا استفاده کنید.

ویژگی کلیدی: این تمپلیت شامل راه‌حل عملی برای مشکل حیاتی هماهنگی RestClient با Eureka است، جایی که Eureka به‌اشتباه از RestClient.Builder دارای @LoadBalanced استفاده می‌کرد و با خطای "No instances available for localhost" مواجه می‌شد. راه‌حل، تفکیک Builderها است: یک Builder ساده با @Primary برای استفاده داخلی Eureka و یک Builder با @LoadBalanced برای کدهای برنامه.

این تمپلیت برای تیم‌هایی ایده‌آل است که می‌خواهند روش‌های مختلف را ارزیابی کنند یا یک مثال یکپارچه برای توسعه‌دهندگان خود فراهم آورند.

---

### ساختار پروژه

```
consumer/
├── src/main/java/com/example/consumer/
│   ├── ConsumerApplication.java          # کلاس اصلی
│   ├── feign/                            # پیاده‌سازی Feign
│   │   ├── ProviderFeignClient.java      # اینترفیس Feign
│   │   └── FeignController.java          # نقطه‌ی پایانی REST
│   ├── restclient/                       # پیاده‌سازی RestClient
│   │   ├── RestClientConfig.java         # پیکربندی RestClient (راه‌حل مشکل)
│   │   ├── ProviderRestClient.java       # سرویس استفاده‌کننده از RestClient
│   │   └── RestClientController.java
│   ├── resttemplate/                     # پیاده‌سازی RestTemplate
│   │   ├── RestTemplateConfig.java       # RestTemplate با LoadBalanced
│   │   ├── RestTemplateClient.java       # سرویس استفاده‌کننده از RestTemplate
│   │   └── RestTemplateController.java
│   ├── webclient/                        # پیاده‌سازی WebClient
│   │   ├── WebClientConfig.java          # WebClient با LoadBalanced
│   │   ├── ProviderWebClient.java        # سرویس استفاده‌کننده از WebClient
│   │   └── WebClientController.java
│   └── httpinterface/                    # HTTP Interface با آداپتورها
│       ├── ProviderHttpInterface.java    # اینترفیس قرارداد
│       ├── HttpInterfaceConfig.java      # ساخت پروکسی‌ها (RestClient, RestTemplate)
│       └── HttpInterfaceController.java  # نقطه‌ی پایانی REST (از Bean دارای @Primary استفاده می‌کند)
└── src/main/resources/
└── application.yaml                  # پیکربندی (پورت، تنظیمات Eureka)
```

---

### پیش‌نیازها

- جاوا ۱۷ یا بالاتر (پروژه با جاوا ۲۵ تست شده، اما با ۱۷+ کار می‌کند)
- Maven ۳.۸ یا بالاتر
- سرور Eureka که روی پورت 8888 اجرا شود (در همین مخزن در پوشه‌ی /eureka قرار دارد – ابتدا آن را اجرا کنید)
- سرویس دهنده (provider) که روی پورت 8081 اجرا شود (در همین مخزن در پوشه‌ی /provider قرار دارد)

---

### نحوه اجرا

#### مرحله ۱: اجرای سرور Eureka

```bash
cd eureka
mvn spring-boot:run
```

داشبورد Eureka در آدرس http://localhost:8888 قابل دسترس خواهد بود.

#### مرحله ۲: اجرای سرویس دهنده

```bash
cd provider
mvn spring-boot:run
```

سرویس دهنده خود را در Eureka ثبت کرده و روی http://localhost:8081 شروع به کار می‌کند.

#### مرحله ۳: اجرای سرویس مصرف‌کننده

```bash
cd consumer
mvn spring-boot:run
```

سرویس مصرف‌کننده خود را در Eureka ثبت کرده و روی http://localhost:8080 شروع به کار می‌کند.

#### مرحله ۴: تست نقاط پایانی

پس از اجرای تمام سرویس‌ها، می‌توانید نقاط پایانی زیر را در سرویس مصرف‌کننده فراخوانی کنید:

| نوع کلاینت       | آدرس                                                        |
|-------------------|-------------------------------------------------------------|
| Feign             | GET http://localhost:8080/api/feign/instance                |
| RestClient        | GET http://localhost:8080/api/rest-client/instance          |
| RestTemplate      | GET http://localhost:8080/api/rest-template/instance        |
| WebClient         | GET http://localhost:8080/api/web-client/instance           |
| Http Interface    | GET http://localhost:8080/api/http-interface/instance       |

همه‌ی نقاط پایانی یک رشته مانند زیر برمی‌گردانند:

```
Instance served by Port: 8081. Instance ID: 3b8f4c2a-...
```

---

### توضیح دقیق هر روش

#### 1. OpenFeign
- اعلانی: اینترفیس را با annotation‌های Spring Cloud تعریف می‌کنید.
- کشف سرویس: از Eureka برای یافتن سرویس "provider" استفاده می‌کند.
- یکپارچگی: پشتیبانی داخلی از تعادل بار و تاب‌آوری.
- مناسب برای: ارتباط بین میکروسرویس‌ها در اکوسیستم Spring Cloud.

#### 2. RestClient (مدرن)
- معرفی شده در: Spring Boot 3.2 / Spring Framework 6.1.
- همگام: مسدودکننده اما با API روان و مدرن.
- تعادل بار: با Eureka از طریق یک Builder مخصوص @LoadBalanced کار می‌کند.
- آینده‌نگر: قرار است جایگزین RestTemplate شود.
- مناسب برای: پروژه‌های جدید که به کلاینت ساده، همگام و با قابلیت سرویس‌یابی نیاز دارند.

#### 3. WebClient (واکنش‌گرا)
- غیرهمگام: از جریان‌های واکنش‌گرا (Mono/Flux) پشتیبانی می‌کند.
- انعطاف‌پذیر: هم به صورت واکنش‌گرا و هم به صورت مسدودکننده (با .block()) قابل استفاده است.
- تعادل بار: از LoadBalancerExchangeFilterFunction با Eureka استفاده می‌کند.
- مناسب برای: اپلیکیشن‌های واکنش‌گرا یا زمانی که به پشتیبانی از استریمینگ/Backpressure نیاز دارید.

#### 4. RestTemplate (قدیمی)
- قدیمی: به نفع RestClient منسوخ شده، اما همچنان پشتیبانی می‌شود.
- همگام: مسدودکننده، API آشنای برای بسیاری از توسعه‌دهندگان.
- تعادل بار: با @LoadBalanced قابل فعال‌سازی است.
- مناسب برای: کدهای موجود که قبلاً از آن استفاده می‌کنند؛ برای پروژه‌های جدید توصیه نمی‌شود.

#### 5. HTTP Interface (@HttpExchange)
- قرارداد یکپارچه: یک اینترفیس با @HttpExchange بنویسید و با آداپتورها پروکسی تولید کنید.
- مستقل از پیاده‌سازی: بدون تغییر اینترفیس، بین RestClient، RestTemplate یا WebClient جابه‌جا شوید.
- مناسب برای: انتزاع جزئیات کلاینت HTTP؛ به ویژه زمانی که می‌خواهید از چندین کلاینت پشتیبانی کنید.

---

### نکات برجسته پیکربندی

#### راه‌حل مشکل RestClient + Eureka

مشکل حیاتی که Eureka با خطای "No instances available for localhost" مواجه می‌شود، با ارائه‌ی دو RestClient.Builder مجزا حل شده است:

```java
@Bean
@Primary
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public RestClient.Builder restClientBuilder(RestClientBuilderConfigurer configurer) {
return configurer.configure(RestClient.builder());
}

@Bean
@LoadBalanced
public RestClient.Builder loadBalancedRestClientBuilder(RestClientBuilderConfigurer configurer) {
return configurer.configure(RestClient.builder());
}
```

- Builder با @Primary: برای ارتباط داخلی Eureka استفاده می‌شود (ساده، بدون تعادل بار).
- Builder با @LoadBalanced: برای کدهای برنامه استفاده می‌شود تا سرویس‌ها را با نام منطقی (مثلاً "http://provider") فراخوانی کند.

بدون این تفکیک، Eureka سعی می‌کند از Builder دارای @LoadBalanced استفاده کند، که به‌اشتباه سعی می‌کند "localhost" را به عنوان یک سرویس پیدا کند و خطا می‌دهد.

#### استفاده از Builder با @LoadBalanced

برای ساختن RestClient با تعادل بار:

```java
@Bean
public RestClient restClient(@LoadBalanced RestClient.Builder builder) {
return builder
.baseUrl("http://provider") // نام منطقی سرویس
.build();
}
```

استفاده از @LoadBalanced روی پارامتر ورودی تضمین می‌کند که Builder صحیح تزریق شود.

---

### افزودن سفارشی‌سازی‌ها

هر کلاینت را می‌توان با موارد زیر گسترش داد:

- تایم‌اوت (اتصال، خواندن، نوشتن)
- Interceptor (لاگ‌گیری، احراز هویت)
- مدیریت خطا (نگاشت استثناهای سفارشی)
- منطق تکرار مجدد (با استفاده از Spring Retry یا resilience4j)

برای افزودن این قابلیت‌ها به مستندات رسمی Spring مراجعه کنید.

---

### فناوری‌های استفاده شده

- Spring Boot 4.1.1
- Spring Cloud 2025.1.3 (برای OpenFeign و Eureka)
- Java 25 (سازگار با ۱۷+)
- Maven برای ساخت
- Lombok برای کاهش کدهای تکراری

---

### مجوز

این پروژه به عنوان یک تمپلیت ارائه شده است – می‌توانید آزادانه از آن استفاده کرده و برای اهداف خود تغییر دهید.