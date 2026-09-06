# Consumer Service – REST Client Template

[English](#english-version) | [فارسی](#نسخه-فارسی)

---

## English Version

### Overview

This project is a **template** microservice that demonstrates **four different ways** to consume a REST API in a Spring Boot application:

1. **OpenFeign** – Declarative REST client (Spring Cloud)
2. **RestClient** – Modern synchronous HTTP client (Spring Boot 3.2+)
3. **WebClient** – Reactive, non-blocking client (Spring WebFlux)
4. **RestTemplate** – Legacy synchronous client (maintained for backward compatibility)

Additionally, it showcases the **HTTP Interface** feature (`@HttpExchange`) which allows you to define a client contract once and use it with any of the above implementations via adapters.

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
│   │   ├── RestClientConfig.java         # RestClient bean configuration
│   │   ├── ProviderRestClient.java       # Service using RestClient
│   │   └── RestClientController.java
│   ├── resttemplate/                     # RestTemplate implementation
│   │   ├── RestTemplateConfig.java       # RestTemplate bean config (uses RestTemplateBuilder)
│   │   ├── RestTemplateClient.java       # Service using RestTemplate
│   │   └── RestTemplateController.java
│   ├── webclient/                        # WebClient implementation
│   │   ├── WebClientConfig.java          # WebClient bean config (manual)
│   │   ├── ProviderWebClient.java        # Service using WebClient
│   │   └── WebClientController.java
│   └── httpinterface/                    # HTTP Interface with adapters
│       ├── ProviderHttpInterface.java    # Contract interface
│       ├── HttpInterfaceConfig.java      # Creates 3 proxy beans (WebClient, RestClient, RestTemplate)
│       └── HttpInterfaceController.java  # REST endpoint (uses @Primary bean)
└── src/main/resources/
└── application.yaml                  # Configuration (port, app name)
```

---

### Prerequisites

- **Java 17+** (project uses Java 25, but works with 17+)
- **Maven 3.8+**
- **Provider service** running on port `8081` (provided in the same repository under `/provider`)

---

### How to Run

#### Step 1: Start the Provider Service

```bash
cd provider
mvn spring-boot:run
```

The provider will start on `http://localhost:8081`.

#### Step 2: Start the Consumer Service

```bash
cd consumer
mvn spring-boot:run
```

The consumer will start on `http://localhost:8080`.

#### Step 3: Test the Endpoints

Once both applications are running, you can call the following endpoints:

| Client Type       | Endpoint                                      |
|-------------------|-----------------------------------------------|
| Feign             | `GET http://localhost:8080/api/feign/instance` |
| RestClient        | `GET http://localhost:8080/api/rest-client/instance` |
| RestTemplate      | `GET http://localhost:8080/api/rest-template/instance` |
| WebClient         | `GET http://localhost:8080/api/web-client/instance` |
| Http Interface    | `GET http://localhost:8080/api/http-interface/instance` |

All endpoints return a string like:

```
Instance served by Port: 8081. Instance ID: 3b8f4c2a-...
```

---

### Detailed Explanation of Each Approach

#### 1. OpenFeign
- **Declarative**: Define an interface with Spring Cloud annotations.
- **Automatic service discovery**: Works with Eureka/Consul, but here we use a fixed URL.
- **Integration**: Built-in support for load balancing and resilience (with Spring Cloud Circuit Breaker).
- **Best for**: Microservice-to-microservice communication in a Spring Cloud ecosystem.

#### 2. RestClient (Modern)
- **Introduced in**: Spring Boot 3.2 / Spring Framework 6.1.
- **Synchronous**: Blocking, but with a fluent and modern API.
- **Performance**: More efficient than RestTemplate (uses Reactor Netty under the hood when available).
- **Future-proof**: Intended to replace RestTemplate.
- **Best for**: New projects that need a simple, synchronous HTTP client.

#### 3. WebClient (Reactive)
- **Reactive**: Non-blocking, supports reactive streams (Mono/Flux).
- **Flexible**: Can be used both reactively and in a blocking way (with `.block()`).
- **Performance**: Handles high concurrency with fewer threads.
- **Best for**: Reactive applications or when you need streaming/backpressure support.

#### 4. RestTemplate (Legacy)
- **Legacy**: Deprecated in favor of RestClient, but still supported.
- **Synchronous**: Blocking, familiar API for many developers.
- **Best for**: Existing codebases that already use it; avoid for new projects.

#### 5. HTTP Interface (`@HttpExchange`)
- **Unified contract**: Write a single interface using `@HttpExchange` and generate proxies with adapters.
- **Implementation-agnostic**: Switch between WebClient, RestClient, or RestTemplate without changing the interface.
- **Best for**: Abstracting HTTP client details; especially useful when you want to support multiple clients.

---

### Configuration Highlights

#### RestTemplateBuilder in Spring Boot 4.1.1

In Spring Boot 4.0+, `RestTemplateBuilder` has moved to the `spring-boot-starter-restclient` module. The configuration in `RestTemplateConfig` uses:

```java
@Bean
public RestTemplate restTemplate(RestTemplateBuilder builder) {
return builder.build();
}
```

This works because the `spring-boot-starter-restclient` dependency provides the builder bean.

#### WebClient without Auto-Configuration

Because this project uses `spring-boot-starter-web` (servlet-based), the `WebClientAutoConfiguration` is **not** automatically enabled. Therefore, we manually create the builder and WebClient beans in `WebClientConfig`:

```java
@Bean
public WebClient.Builder webClientBuilder() {
return WebClient.builder().baseUrl("http://localhost:8081");
}
```

This is a common pattern when you need WebClient in a non‑reactive application.

---

### Adding Customizations

Each client can be extended with:

- **Timeouts** (connection, read, write)
- **Interceptors** (logging, authentication)
- **Error handling** (custom exception mapping)
- **Retry logic** (using Spring Retry or resilience4j)

Refer to the official Spring documentation for each client to add these features.

---

### Technologies Used

- **Spring Boot 4.1.1**
- **Spring Cloud 2025.1.3** (for OpenFeign)
- **Java 25** (compatible with 17+)
- **Maven** for build
- **Lombok** for reducing boilerplate

---

### License

This project is provided as a template – you are free to use and modify it for your own purposes.

---

## نسخه فارسی

### بررسی کلی

این پروژه یک **تمپلیت** برای میکروسرویس مصرف‌کننده است که **چهار روش متفاوت** برای فراخوانی REST API در اپلیکیشن Spring Boot را نشان می‌دهد:

1. **OpenFeign** – کلاینت اعلانی (Spring Cloud)
2. **RestClient** – کلاینت همگام مدرن (Spring Boot 3.2+)
3. **WebClient** – کلاینت واکنش‌گرا و غیرهمگام (Spring WebFlux)
4. **RestTemplate** – کلاینت همگام قدیمی (برای سازگاری با کدهای قبلی)

علاوه بر این، قابلیت **HTTP Interface** (`@HttpExchange`) را نیز به نمایش می‌گذارد که به شما اجازه می‌دهد یک قرارداد کلاینت را یک بار تعریف کرده و با استفاده از آداپتورها، آن را با هر یک از پیاده‌سازی‌های بالا استفاده کنید.

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
│   │   ├── RestClientConfig.java         # پیکربندی Bean RestClient
│   │   ├── ProviderRestClient.java       # سرویس استفاده‌کننده از RestClient
│   │   └── RestClientController.java
│   ├── resttemplate/                     # پیاده‌سازی RestTemplate
│   │   ├── RestTemplateConfig.java       # پیکربندی Bean RestTemplate (با RestTemplateBuilder)
│   │   ├── RestTemplateClient.java       # سرویس استفاده‌کننده از RestTemplate
│   │   └── RestTemplateController.java
│   ├── webclient/                        # پیاده‌سازی WebClient
│   │   ├── WebClientConfig.java          # پیکربندی Bean WebClient (دستی)
│   │   ├── ProviderWebClient.java        # سرویس استفاده‌کننده از WebClient
│   │   └── WebClientController.java
│   └── httpinterface/                    # HTTP Interface با آداپتورها
│       ├── ProviderHttpInterface.java    # اینترفیس قرارداد
│       ├── HttpInterfaceConfig.java      # ساخت ۳ Bean پروکسی (WebClient, RestClient, RestTemplate)
│       └── HttpInterfaceController.java  # نقطه‌ی پایانی REST (از Bean دارای @Primary استفاده می‌کند)
└── src/main/resources/
└── application.yaml                  # پیکربندی (پورت، نام اپلیکیشن)
```

---

### پیش‌نیازها

- **جاوا ۱۷** یا بالاتر (پروژه با جاوا ۲۵ تست شده، اما با ۱۷+ کار می‌کند)
- **Maven ۳.۸** یا بالاتر
- **سرویس دهنده (Provider)** که روی پورت `8081` اجرا شود (در همین مخزن در پوشه‌ی `/provider` قرار دارد)

---

### نحوه اجرا

#### مرحله ۱: اجرای سرویس دهنده

```bash
cd provider
mvn spring-boot:run
```

سرویس دهنده روی `http://localhost:8081` شروع به کار می‌کند.

#### مرحله ۲: اجرای سرویس مصرف‌کننده

```bash
cd consumer
mvn spring-boot:run
```

سرویس مصرف‌کننده روی `http://localhost:8080` شروع به کار می‌کند.

#### مرحله ۳: تست نقاط پایانی

پس از اجرای هر دو اپلیکیشن، می‌توانید نقاط پایانی زیر را فراخوانی کنید:

| نوع کلاینت       | آدرس                                                        |
|-------------------|-------------------------------------------------------------|
| Feign             | `GET http://localhost:8080/api/feign/instance`              |
| RestClient        | `GET http://localhost:8080/api/rest-client/instance`        |
| RestTemplate      | `GET http://localhost:8080/api/rest-template/instance`      |
| WebClient         | `GET http://localhost:8080/api/web-client/instance`         |
| Http Interface    | `GET http://localhost:8080/api/http-interface/instance`     |

همه‌ی نقاط پایانی یک رشته مانند زیر برمی‌گردانند:

```
Instance served by Port: 8081. Instance ID: 3b8f4c2a-...
```

---

### توضیح دقیق هر روش

#### 1. OpenFeign
- **اعلانی**: اینترفیس را با annotation‌های Spring Cloud تعریف می‌کنید.
- **کشف سرویس خودکار**: با Eureka/Consul کار می‌کند، اما در اینجا از URL ثابت استفاده شده است.
- **یکپارچگی**: پشتیبانی داخلی از تعادل بار و تاب‌آوری (با Spring Cloud Circuit Breaker).
- **مناسب برای**: ارتباط بین میکروسرویس‌ها در اکوسیستم Spring Cloud.

#### 2. RestClient (مدرن)
- **معرفی شده در**: Spring Boot 3.2 / Spring Framework 6.1.
- **همگام**: مسدودکننده (Blocking) اما با API روان و مدرن.
- **عملکرد**: کارآمدتر از RestTemplate (در پشت صحنه از Reactor Netty استفاده می‌کند).
- **آینده‌نگر**: قرار است جایگزین RestTemplate شود.
- **مناسب برای**: پروژه‌های جدید که به یک کلاینت ساده و همگام نیاز دارند.

#### 3. WebClient (واکنش‌گرا)
- **غیرهمگام**: از جریان‌های واکنش‌گرا (Mono/Flux) پشتیبانی می‌کند.
- **انعطاف‌پذیر**: هم به صورت واکنش‌گرا و هم به صورت مسدودکننده (با `.block()`) قابل استفاده است.
- **عملکرد**: همزمانی بالا را با تعداد تردهای کمتری مدیریت می‌کند.
- **مناسب برای**: اپلیکیشن‌های واکنش‌گرا یا زمانی که به پشتیبانی از استریمینگ/Backpressure نیاز دارید.

#### 4. RestTemplate (قدیمی)
- **قدیمی**: به نفع RestClient منسوخ شده، اما همچنان پشتیبانی می‌شود.
- **همگام**: مسدودکننده، API آشنای برای بسیاری از توسعه‌دهندگان.
- **مناسب برای**: کدهای موجود که قبلاً از آن استفاده می‌کنند؛ برای پروژه‌های جدید توصیه نمی‌شود.

#### 5. HTTP Interface (`@HttpExchange`)
- **قرارداد یکپارچه**: یک اینترفیس با `@HttpExchange` بنویسید و با آداپتورها پروکسی تولید کنید.
- **مستقل از پیاده‌سازی**: بدون تغییر اینترفیس، بین WebClient، RestClient یا RestTemplate جابه‌جا شوید.
- **مناسب برای**: انتزاع جزئیات کلاینت HTTP؛ به ویژه زمانی که می‌خواهید از چندین کلاینت پشتیبانی کنید.

---

### نکات برجسته پیکربندی

#### RestTemplateBuilder در Spring Boot 4.1.1

در Spring Boot 4.0+، `RestTemplateBuilder` به ماژول `spring-boot-starter-restclient` منتقل شده است. پیکربندی در `RestTemplateConfig` از کد زیر استفاده می‌کند:

```java
@Bean
public RestTemplate restTemplate(RestTemplateBuilder builder) {
return builder.build();
}
```

این کد کار می‌کند زیرا وابستگی `spring-boot-starter-restclient` Bean مربوط به Builder را فراهم می‌کند.

#### WebClient بدون Auto-Configuration

از آنجایی که این پروژه از `spring-boot-starter-web` (مبتنی بر Servlet) استفاده می‌کند، `WebClientAutoConfiguration` به‌طور خودکار فعال **نمی‌شود**. بنابراین، ما به‌صورت دستی Builder و Beanهای WebClient را در `WebClientConfig` می‌سازیم:

```java
@Bean
public WebClient.Builder webClientBuilder() {
return WebClient.builder().baseUrl("http://localhost:8081");
}
```

این یک الگوی رایج زمانی است که در یک اپلیکیشن غیرواکنش‌گرا به WebClient نیاز دارید.

---

### افزودن سفارشی‌سازی‌ها

هر کلاینت را می‌توان با موارد زیر گسترش داد:

- **تایم‌اوت** (اتصال، خواندن، نوشتن)
- **Interceptor** (لاگ‌گیری، احراز هویت)
- **مدیریت خطا** (نگاشت استثناهای سفارشی)
- **منطق تکرار مجدد** (با استفاده از Spring Retry یا resilience4j)

برای افزودن این قابلیت‌ها به مستندات رسمی Spring مراجعه کنید.

---

### فناوری‌های استفاده شده

- **Spring Boot 4.1.1**
- **Spring Cloud 2025.1.3** (برای OpenFeign)
- **Java 25** (سازگار با ۱۷+)
- **Maven** برای ساخت
- **Lombok** برای کاهش کدهای تکراری

---

### مجوز

این پروژه به عنوان یک تمپلیت ارائه شده است – می‌توانید آزادانه از آن استفاده کرده و برای اهداف خود تغییر دهید.