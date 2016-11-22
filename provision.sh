SETUP_DIR='/vagrant/setup'
SETUP_SPARK_DIR='/usr/local/spark'
SETUP_SBT_DIR='/usr/local/sbt'
SPARK_LINK='http://www.us.apache.org/dist/spark/spark-2.0.2/spark-2.0.2-bin-hadoop2.7.tgz'
SCALA_LINK='http://downloads.lightbend.com/scala/2.11.8/scala-2.11.8.deb'
SBT_LINK='https://dl.bintray.com/sbt/native-packages/sbt/0.13.13/sbt-0.13.13.tgz'

###########################################################################
# ultility functions
###########################################################################
setupEnvVar () {
    local var_name=$1
    local var_val=$2
    echo "export $var_name=$var_val" >> ~/.bashrc
    echo "export $var_name=$var_val" >> /home/ubuntu/.bashrc
    export $var_name=$var_val
}

printMsg (){
    local msg=$1    
    local d=$(date);
    echo  "$d: $msg"
    
}

printHeader () {
    local text=$1    
    echo '********************'
    echo "* $text"
    echo '********************'
}

downloadFile () {
    local f=$1
    local name=$2
    if [ -f $f ];
    then
        echo "$f is downloaded"
    else
        echo "Start download $f"
	wget $f -O $name
    fi
}

###########################################################################
# main functions
###########################################################################

setupSofts(){
    printHeader 'setupSofts'
    apt-get update
    apt-get install openjdk-8-jdk nmap htop -y 
    apt-get install -f
    tmpval='/usr/lib/jvm/java-8-openjdk-amd64'
    setupEnvVar 'JAVA_HOME' $tmpval
    setupEnvVar 'PATH' "$JAVA_HOME/bin:$PATH"
}

setupScala(){
    printHeader 'Install scala'
    downloadFile $SCALA_LINK scala.deb
    dpkg -i ./scala.deb
    scala -version
}

setupSpark(){
    printHeader 'Install spark'
    downloadFile $SPARK_LINK spark.tar.gz
    mkdir $SETUP_SPARK_DIR 
    tar -xzf spark.tar.gz -C $SETUP_SPARK_DIR --strip-components 1

    tmpval="$PATH:$SETUP_SPARK_DIR/bin"
    setupEnvVar 'PATH' $tmpval  
}

setupSbt(){
    printMsg 'setupSbt';
    downloadFile $SBT_LINK sbt.tar.gz
    mkdir $SETUP_SBT_DIR 
    tar -xzf sbt.tar.gz -C $SETUP_SBT_DIR --strip-components 1

    tmpval="$PATH:$SETUP_SBT_DIR/bin"
    setupEnvVar 'PATH' $tmpval

}

setup () {
    printHeader 'SETUP SYSTEM'
    printMsg '- Create hadoop user'
    mkdir $SETUP_DIR
    cd $SETUP_DIR
    sudo adduser hadoop --gecos "Hadoop User,RoomNumber,WorkPhone,HomePhone" --disabled-password
    echo "hadoop:hadoop" | sudo chpasswd

    ##############
    setupSofts     
    setupScala
    setupSpark
    setupSbt

    ##############
    printHeader 'RUN DEMO'
    downloadFile https://www.gutenberg.org/ebooks/1661.txt.utf-8 SherlockHolmes.txt
}

###########################################################################
# Run setup
###########################################################################
setup
