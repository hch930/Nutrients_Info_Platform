# Nutrients_Info_Platform
term project for SoftwareDesign <br />
- email address : jms393497@gmail.com <br />
- Demo Video : https://youtu.be/meTqQoW314o <br />
- blog : https://popcorn16.tistory.com/ <br />

## Introduction
This is an android application for audience who likes to watch movies in theaters.
1. It shows box office ranking of korea independent and commercial films.
2. It shows theaters around your current location.
3. You can review the movie theater.
4. You can check other people's reviews and manage own reviews.

## Development Environment
- mysql @8.0.18
- Apache2 Server
- php
- Android Studio @3.5.3

## Application Version
- minSdkVersion : 15
- targetSdkVersion : 26

## APIs
- open API of Korean Film Council (homepage : https://www.kobis.or.kr/kobisopenapi/homepg/main/main.do) <br />
If you want to get box office ranking, sign up to this homepage and get own key. <br />
- open API of NAVER (homepage : https://developers.naver.com/main/) <br />
If you want to get Movie Poster, sign up to this homepage and get own key. <br />
- google map <br />
If you want to get locations of theaters, sign up to google cloud platform and get own key.

## Database table information
database table backup file is in /server/backup.sql <br />
mysql -u [account] -p [database] < backup.sql
