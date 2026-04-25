![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Vault](https://img.shields.io/badge/Vault-ffffff?logo=vault&style=for-the-badge&color=9c9c9c&logoColor=000000)

[![java](https://img.shields.io/badge/Java-17-important)](https://adoptium.net/es/temurin/releases/?os=windows&arch=any&package=jdk&version=17)
![spring boot](https://img.shields.io/badge/Spring-3.5.0-green?logo=springboot)
[![web](https://img.shields.io/badge/Spring_Boot_WebFlux-6.2.7-green)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web/3.2.5)

[![mysql](https://img.shields.io/badge/mysql_connector-8.3.0-blue?logo=mysql)](https://mvnrepository.com/artifact/com.mysql/mysql-connector-j/8.3.0)
[![mapstruct](https://img.shields.io/badge/mapstruct-1.6.3-yellow)](https://mvnrepository.com/artifact/org.mapstruct/mapstruct/1.5.5.Final)
![r2dbc](https://img.shields.io/badge/r2dbc-1.0.4-yellow?logo)
[![lombok](https://img.shields.io/badge/Lombok-1.18.38-yellow)](https://mvnrepository.com/artifact/org.projectlombok/lombok/1.18.32)

![maven](https://img.shields.io/badge/Maven-3.9.9-red?logo=apache-maven)
[![vault](https://img.shields.io/badge/Vault-1.19.5-lightgrey?logo=vault)](https://developer.hashicorp.com/vault/install)


# API STUDENT

Api creado para registro de estudiantes en MySQL, se ha desarrollado para 2 endpoint:
* Consulta de estudiantes activos.
* Registro de estudiantes.

## Instalación
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/josephmn/msv-maven-student.git

2. Abrir el proyecto en tu IDE favorito (IntelliJ, Eclipse, etc.).
3. Configurar la base de datos MySQL:
    - Crear una base de datos llamada `school`.
    - Ejecutar el siguiente script SQL para crear la tabla `student`:
      ```sql
      CREATE TABLE IF NOT EXISTS users (
      id BIGINT IDENTITY(1,1) PRIMARY KEY,
      ruc VARCHAR(15) NOT NULL,
      username VARCHAR(50) NOT NULL,
      password VARCHAR(255) NOT NULL,
      email VARCHAR(100) NOT NULL,
      role VARCHAR(20) NOT NULL DEFAULT 'USER',
      enabled BIT NOT NULL DEFAULT 1,
      created_at DATETIME DEFAULT GETDATE(),
      updated_at DATETIME DEFAULT GETDATE()
      );
      
      CREATE UNIQUE INDEX uk_users_ruc ON users (ruc);
      CREATE UNIQUE INDEX uk_users_username ON users (username);
      CREATE UNIQUE INDEX uk_users_email ON users (email);

      CREATE TABLE IF NOT EXISTS student (
         id INT AUTO_INCREMENT PRIMARY KEY,
         document VARCHAR(15),
         name VARCHAR(50),
         last_name VARCHAR(50),
         status BOOLEAN,
         age int
      );
      
      INSERT INTO student (document, name, last_name, status, age)
      VALUES
      ('11111111','Julian','Fernandez',1,25),
      ('22222222','Felipe','Vicente',0,35),
      ('33333333','Alexander','Gutierrez',1,26),
      ('44444444','Felix','Saravia',0,19),
      ('55555555','Alberto','Peña',1,32),
      ('66666666','Cristian','Dominguez',0,35),
      ('77777777','Ronaldo','Flores',1,40),
      ('88888888','Juan','Enrique',0,21);


      CREATE TABLE IF NOT EXISTS teacher (
         id INT AUTO_INCREMENT PRIMARY KEY,
         document VARCHAR(15),
         name VARCHAR(50),
         last_name VARCHAR(50),
         status BOOLEAN,
         age int
      );
   
      INSERT INTO teacher (document, name, last_name, status, age)
      VALUES
      ('45453245','Jhon','James',1,33),
      ('46576543','Julian','Valdivieso',0,35);
      
      CREATE TABLE IF NOT EXISTS users (
         id BIGINT AUTO_INCREMENT PRIMARY KEY,
         ruc VARCHAR(15) NOT NULL UNIQUE,
         username VARCHAR(50) NOT NULL UNIQUE,
         password VARCHAR(255) NOT NULL,
         email VARCHAR(100) NOT NULL UNIQUE,
         role VARCHAR(20) NOT NULL DEFAULT 'USER',
         enabled BOOLEAN NOT NULL DEFAULT TRUE,
         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
      );
      ```
4. Instalar Vault para el almacenamiento de secretos:
    - Descargar e instalar Vault para su SO, desde [Vault](https://www.vaultproject.io/downloads).

5. Configurar Vault en windows:
    - Descomprimir el archivo descargado en `C:\vault_1.19.5`:
    - Luego configurar la variable de entorno con el siguiente comando:
      ```bash
      setx VAULT_ADDR "http://localhost:8200"
      ```
    - Para el path de la variable de entorno, se debe colocar la ruta del primer paso:
      ![img.png](imagen/img-0.png)
    - Luego abrir una terminal y ejecutar el siguiente comando para iniciar el servidor de Vault:
      ```bash
      vault server --dev --dev-root-token-id="00000000-0000-0000-0000-000000000000"
      ```
    - En la raíz del proyecto, crear un archivo llamado `msv-maven-student.json` y agregar el siguiente contenido:
      ```json
      {
        "azure.servicebus.connection-string": "Endpoint=sb://studentdemo.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=f7V14KoGqMQt8Oy40PJuQddvtiBIBGV9X+ASbJbo6U1=",
        "azure.servicebus.namespace": "studentdemo",
        "azure.servicebus.subscription-name": "studentdemo",
        "azure.servicebus.topic-name": "student-topic",
        "jwt.secret": "tu_clave_secreta_para_jwt",
        "key.private": "KEY_PRIVATE",
        "key.public": "KEY_PUBLIC",
        "spring.r2dbc.password": "tu_password",
        "spring.r2dbc.url": "r2dbc:mysql://localhost:3306/tu_base_de_datos",
        "spring.r2dbc.username": "tu_username"
      }
      ```
      > Reemplazar `tu_base_de_datos`, `tu_username` y `tu_password` con los valores correspondientes a tu base de datos MySQL, asi como los datos para el jwt `tu_clave_secreta_para_jwt` y el Service Bus **antes de copiar y ejecutar en la ventana de cmd**.
      
      Reemplazar **KEY_PRIVATE**
      ````
      -----BEGIN RSA PRIVATE KEY-----
      MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDhb1EZwQ/6iHldJ0xdRWd+0Y5nULcj1yxVRr5fpR8cl0AGAC2Bg3jUI3jAZsMcIeAeIk0TVM0bUhgrUR73HmjWUET5BHGwfLlOy5o45VXfLIsxDHkqEdOHiPzvz4LWhL6Wp0Yn7H+Kwrh5mCavelagNykOUpJ+8G1oSze//W+FbNQl7oV6WKSwVIgokzSU2KNYPN0AXm/X011lgbeEpJ2nr8pUAHeV/bnz1kaR4n/yoKVsjpVF3n3G79ExYB5QdSqvwUk09JvHPczjR49svv2W+zZhIoAQ3rRganNM1QUWbLm4rPkSIAs1QP1+5kjiQCU84oYsh1CyYdnVZZv1bgitAgMBAAECggEALexVypfML/62LnZPdZ6qVyjMSBqmvCvcsaCALo6+GD5tgetuWbhVGG4DbrpgW65A8BKPvfTj9YFTQBu94Zx1wdbHPi0cFdZKj5UlXRrG0/hIgJzoX9wSycTrtdSBdD7Fbn4z6fUhBDznhImss8mJtQiXmf3TLv2+Y+USvc8cdULUqj1exKP3eSB/baFy5sz2aBKgxBQ3Bbcg4AC9BpSrwGLIJhqMPvznq89fa2FLlS/RiaJSmGATeczLKfJ86BL1MlesnTp80z6u27f4I3UeV96xarytehEKApnsd9/vVCVvnQwx5k0HuUBEJsn0kVJYuPv31K1oZeBYyBudUYBnAQKBgQDjAe7PyjCdiPMB8DfzszGEUjHrIW+Hf7vNr2K/4KQDaPtgrkwODKzwEbHzhPjHwgZsdRNA2qh8J0ySdhUlPMs265It2Y956AzsrwY1ivgxrpiR3MPgEiMgX+U9ocLbPWsiS5PcweLLVzWuQ0jmVsaNcWqx326g9conmlrnT38OLQKBgQD+OfaoqIUkHcjF0AakSoP/ZKwY43zcSL+3sH2njH/OH2aAsr18LJJABLHpF6gXuy8uf3dWPwmPkjqdmz99YC0McuSLZAC4QmTs2iTqtue74xK0cYmkBGYS7ilIKDhxbXTW+TTIFgFTf1tPjsuUvZACDtsmwJDKIrmkoGoCJJn0gQKBgAERHUI0vQ3AewPjUSAAE5d/m2Rzf8avPv60eE5Wi+7IK4ZEy261eIqYSrHOSi0GKGmSE0kWvfv3Y1C85VSLeJkFemZkw1vbT1Q9blYKFSiGnYjx9Km07B1W1gj4HaZSH5LFsCg1cQ9rlYEAJ/ONLNm1Ur8LQcXX4d9Vxl+X7AzxAoGAC8zg+B8qXuzK/vOS+1lCGz6vBlC44Pi3fW1Bx98rp4OpnFuvk+31s+cD4w/oC4HyxLPB9EzkwrdE5T/piIZIUQWcOiQgLG1Yhe3yPLrUstHtcla6ztQtcL+BRwom5IzyGXBj1M3ArgGBWYYFSRgrcQ0Dex8BGYDXIR8ZDJRqWQECgYEAkFhT5q92uD+dFowJ62KCR2P3xBjXALKwP/3q/0FuWHv3iVeTijmklXJCYzOzxzV5wE5h6PxA/N1e7icaZIbE3KnUA9wPY/932UoOKeH4TJXMdGxNkcIgNJbiiht4+DZEgfroFb6Apf78kWqIfGBKH4QaR+xNzUIArRJ5N/jptAo=
      -----END RSA PRIVATE KEY-----
      ````
      Reemplazar **KEY_PUBLIC**
      ````
      -----BEGIN PUBLIC KEY-----
      MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4W9RGcEP+oh5XSdMXUVnftGOZ1C3I9csVUa+X6UfHJdABgAtgYN41CN4wGbDHCHgHiJNE1TNG1IYK1Ee9x5o1lBE+QRxsHy5TsuaOOVV3yyLMQx5KhHTh4j878+C1oS+lqdGJ+x/isK4eZgmr3pWoDcpDlKSfvBtaEs3v/1vhWzUJe6FeliksFSIKJM0lNijWDzdAF5v19NdZYG3hKSdp6/KVAB3lf2589ZGkeJ/8qClbI6VRd59xu/RMWAeUHUqr8FJNPSbxz3M40ePbL79lvs2YSKAEN60YGpzTNUFFmy5uKz5EiALNUD9fuZI4kAlPOKGLIdQsmHZ1WWb9W4IrQIDAQAB
      -----END PUBLIC KEY-----
      ````

    - Luego, en la terminal, ejecutar el siguiente comando para escribir los secretos en Vault:
      ```bash
      vault kv put secret/msv-maven-student/prod @msv-maven-student-prod.json
      ```
    - Para subirlo a Vault en Docker:
      * Primero subir a docker el archivo *.json con los secretos del paso 5:
        ```bash
        docker cp .\msv-maven-student-dev.json vault-server:/tmp/msv-maven-student-dev.json
        ```
      * Luego ejecutar el siguiente comando para escribir los secretos en Vault:
        ```bash
        docker exec -it vault-server /bin/sh
        cd tmp
        vault kv put secret/msv-maven-student/dev @msv-maven-student-dev.json
        ```
6. Ejecutar la aplicación:
    - Asegurarse de que el servidor de Vault esté en ejecución, deberías poder resolver la siguiente URL:
      ```
      http://localhost:8200
      ```
      Ingresar con Method Token y el token `00000000-0000-0000-0000-000000000000`.
      Luego, ingresar a la ruta `secret/msv-maven-student/prod` y en la pestaña **Secret**, al ingresar deberías ver los secretos que se han guardado.
      ![img-1.png](imagen/img-1.png)

    - Ejecutar la aplicación desde tu IDE o usando maven:
      ```bash
      mvn clean install
      mvn spring-boot:run
      ```
7. Ejecutar los endpoints:
    - Para ejecutar los endpoints, puedes usar Postman o cualquier cliente HTTP.
    - También puedes usar swagger que esta en el siguiente enlace, cuando ejecutes el servicio http://localhost:8082/swagger-ui/swagger-ui/index.html
    - No olvidar que los endpoints funcionan con JWT, por lo que primero debes obtener un token de autenticación usando el endpoint de login, y luego usar ese token para acceder a los demás endpoints.
      ## 🧑🏻‍🎓 Estudiante
        - Estudiante por id = 1:
      ```cUrl
      curl -X 'GET' \
        'http://localhost:8082/api/v1/students/1' \
        -H 'accept: application/json'
      ```
        - Actualizar estudiante por id:
      ```cUrl
      curl -X 'PUT' \
        'http://localhost:8082/api/v1/students/10' \
        -H 'accept: application/json' \
        -H 'Content-Type: application/json' \
        -d '{
        "document": "78765456",
        "name": "Alberto",
        "lastName": "Rodriguez",
        "status": true,
        "age": 26
      }'
      ```
        - Eliminar estudiante por id = 12:
      ```cUrl
      curl -X 'DELETE' \
        'http://localhost:8082/api/v1/students/12' \
        -H 'accept: application/json'
      ```
        - Obtener todos los estudiantes:
      ```cUrl
      curl -X 'GET' \
        'http://localhost:8082/api/v1/students' \
        -H 'accept: application/json'
      ```
        - Registrar un nuevo estudiante:
      ```cUrl
      curl -X 'POST' \
        'http://localhost:8082/api/v1/students' \
        -H 'accept: application/json' \
        -H 'Content-Type: application/json' \
        -d '{
        "document": "78653423",
        "name": "Jhon",
        "lastName": "James",
        "status": true,
        "age": 30
      }'
      ```
        - Obtener los estudiantes activos:
      ```cUrl
      curl -X 'GET' \
        'http://localhost:8082/api/v1/students/actives' \
        -H 'accept: application/json'
      ```
        - Obtener estudiantes por nombre = alex:
      ```cUrl
      curl -X 'GET' \
        'http://localhost:8082/api/v1/students/list/alex' \
        -H 'accept: application/json'
      ```
      ## 🧑🏻‍🏫 Profesor
        - Obtener todos los profesores:
      ```cUrl
      curl -X 'GET' \
        'http://localhost:8082/api/v1/teachers' \
        -H 'accept: application/json'
      ```
        - Registrar un nuevo profesor:
      ```cUrl
      curl -X 'POST' \
        'http://localhost:8082/api/v1/teachers' \
        -H 'accept: application/json' \
        -H 'Content-Type: application/json' \
        -d '{
        "document": "45453245",
        "name": "Jhon",
        "lastName": "James"
      }'
      ```
      ## 🧑🏻‍💻 Usuario
        - Registrar un nuevo usuario:
      ```cUrl
      curl -X 'POST' \
        'http://localhost:8082/api/v1/auth/register' \
        -H 'accept: application/json' \
        -H 'Content-Type: application/json' \
        -d '{
        "ruc": "12345678901",
        "username": "testuser",
        "password": "password123",
        "email": "testuser@example.com",
        "role": "USER"
      }'
      ```
        - Login usuario:
      ```cUrl
        curl -X 'POST' \
          'http://localhost:8082/api/v1/auth/login/12345678901' \
          -H 'accept: application/json' \
          -H 'Content-Type: application/json' \
          -d '{
          "username": "testuser",
          "password": "password123"
        }'
        ```
## Uso

Microservicio Maven Student, esta es solo una primera versión para el objetivo que fue desarrollado.

## Contribuir

Por el momento no hay contribuciones.