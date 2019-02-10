# Emotracker
# Required:
- java8 
- npm 
- nodejs
# Quick start
1. Download emotracker.tar.gz from this nice repo
2. tar -xvf emotracker.tar.gz
3. cd emotracker
4. ./start.sh
5. wait for it to boot

By default backend will start on 8080 port. 
To change it:
1. cd emotracker
2. echo 'server.port=7777' > application.properties
3. cd ui
4. echo 'REACT_APP_BASE_URL=http://localhost:7777' > .env.development
