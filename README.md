# jasyptapi
A wrapper API around jasypt to to easily encrypt/decrypt your sensitive data

## Deployment and setup
### Using Maven
Run `mvn spring-boot:run`. No other configuration needed.
### Using Docker
1. Create jar: `mvn clean install`
2. Build image: `docker build -t jasyptapi --build-arg BUILD_DIR=target .`
3. Run image: `docker run -d --name jasyptapi -p 8080:8080 jasyptapi`

Once deployed, the application will be accessible at [http://localhost:8080/jasyptapi/](http://localhost:8080/jasyptapi/)
