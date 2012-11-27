java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=$1 \
-Djava.library.path=lib/ \
-cp bin/:lib/* birdoprey.exterminator.ExterminatorApp \
-debug \
2>> error.txt