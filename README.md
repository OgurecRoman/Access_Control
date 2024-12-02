# Access_Control
! Итоговый проект лежит в ветке webcam !
Сервис для реализации системы контроля присутствия сотрудников на предприятии.
Интеграция с базой данных PostgreSQL и с RTPS-потоками через ffmpeg

# Настройка RTPS-потоков
###### !!! для теста !!!

#### установить nginx

```bash
# nginx.conf

    worker_processes  1;

    error_log  logs/error.log debug;

    events {
        worker_connections  1024;
    }
    rtmp {
        server {
            listen 1935;

            application live {
                live on;                    # Включение RTMP
                record off;                 # Выключение записи

                hls on;                     # Включение HLS
                hls_path C:/nginx/hls;      # Путь для хранения HLS файлов
                hls_fragment 3;             # Длина каждого фрагмента .ts в секундах
                hls_playlist_length 10;     # Продолжительность плейлиста .m3u8
            }
        }
    }
    http {
        server {
            listen 8081;

            location /hls {
                types {
                    application/vnd.apple.mpegurl m3u8;
                    video/mp2t ts;
                }
                root C:/nginx; # Путь до корневой директории, где хранятся файлы hls
                add_header Cache-Control no-cache;
                add_header Access-Control-Allow-Origin *; # Добавляем заголовок CORS
                send_timeout 60s;            # Увеличение тайм-аута для HTTP-запросов
                client_max_body_size 100M;  # Увеличение максимального размера тела запроса
            }
        }
    }
```

#### установить ffmpeg

#### открыть nginx

#### узнать название камеры

```bash
ffmpeg -list_devices true -f dshow -i dummy
```

#### запустить трансляцию
```bash
ffmpeg -f dshow -i video="Name_Your_Camera" -vcodec libx264 -preset veryfast -tune zerolatency -f flv rtmp://localhost/live/demo
```

# Запуск проекта

#### установить Maven
#### установить openCV
#### перейти в директорию demo
```bash
cd demo
```
#### собрать проект
```bash
mvn clean package
```
