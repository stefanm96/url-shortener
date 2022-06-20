# URL Shortener
This is a basic URL Shortener build in Java, Spring Boot and H2.

## How to use locally:
to start the application run: `./gradlew bootRun`

the application will be started on port 8080



## Endpoints:
### Shortening:

Request:
```
POST /short
Content-Type: application/json
```
Request Body:
```json
{
"originalUrl": "https://www.stefan-michel.com",
"customHash": "jobs"
}
```
if customHash is null or blank, a hash will be generated.

Curl:
```
curl -X POST --location "http://localhost:8080/short" \
    -H "Content-Type: application/json" \
    -d "{
            \"originalUrl\": \"https://www.stefan-michel.com\",
            \"customHash\": \"jobs\"
        }"
```

### Analytics:
Request:
```
POST /analytics
Content-Type: application/json
```
Request Body:
```json
{
"url": "http://localhost:8080/jobs"
}
```
Curl:
```
curl -X POST --location "http://localhost:8080/analytics" \
    -H "Content-Type: application/json" \
    -d "{
            \"url\": \"http://localhost:8080/jobs\"
        }"
```

### Redirection:

Request:
```
GET /{hash}
```
will redirect you to the original page.