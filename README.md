# Library Management REST API

Bu layihə Java Spring Boot fənni üçün hazırladığım tapşırıqdır.

Layihənin məqsədi kitabxana sistemində müəllif və kitab məlumatlarını REST API vasitəsilə idarə etməkdir. Məlumatlar MySQL verilənlər bazasında saxlanılır və bütün CRUD əməliyyatları Spring Boot vasitəsilə həyata keçirilir.

---

## İstifadə olunan texnologiyalar

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Git
- GitHub

---

## Layihənin strukturu

Layihə aşağıdakı hissələrdən ibarətdir.

- Controller
- Service
- Repository
- Entity
- MySQL Database

Entity-lər arasında əlaqə yaradılıb.

- Bir müəllifin bir neçə kitabı ola bilər.
- Hər kitab yalnız bir müəllifə məxsusdur.

---

## API imkanları

### Author

- Yeni müəllif əlavə etmək
- Müəllifləri göstərmək
- ID üzrə müəllif tapmaq
- Müəllif məlumatını yeniləmək
- Müəllifi silmək

### Book

- Yeni kitab əlavə etmək
- Kitab əlavə etmək
- Bütün kitabları göstərmək
- ID üzrə kitab tapmaq
- Kitab məlumatını yeniləmək
- Kitabı silmək

---

## İstifadə olunan endpoint-lər

### Author

| Method | Endpoint |
|--------|----------|
| GET | /api/authors |
| GET | /api/authors/{id} |
| POST | /api/authors |
| PUT | /api/authors/{id} |
| DELETE | /api/authors/{id} |

### Book

| Method | Endpoint |
|--------|----------|
| GET | /api/books |
| GET | /api/books/{id} |
| POST | /api/books |
| PUT | /api/books/{id} |
| DELETE | /api/books/{id} |

---

## Layihə zamanı qarşılaşdığım problemlər

Layihə üzərində işləyərkən bir neçə problemlə qarşılaşdım.

İlk öncə "BookService" interfeysində metod adı ilə "BookServiceImpl" daxilindəki metod adı fərqli idi. Buna görə layihə compile olunmurdu. Metod adlarını eyni etdikdən sonra problem aradan qalxdı.

Daha sonra Update və Delete əməliyyatlarında bəzi səhvlər yaranırdı. Servis qatında metodları yenidən yazaraq bu problemi həll etdim.

Layihəni GitHub-a göndərərkən isə aşağıdakı xəta ilə qarşılaşdım.

```
error: src refspec main does not match any
```
Səbəb ilk commit-in yaradılmaması idi. Commit etdikdən sonra layihə uğurla GitHub repository göndərildi.

---

## Repository

Bu repository Java Spring Boot ilə hazırlanmış sadə Library Management REST API layihəsini saxlayır.

---

Hazırlayan: Orxan Adıgözəlov