SETUP_DIR_ROOT='/vagrant/setup'
SETUP_DIR_SPARK='/usr/local/spark'
SETUP_DIR_SBT='/usr/local/sbt'
SETUP_DIR_HADOOP='/usr/local/hadoop'

DOWNLOAD_LINK_SPARK='http://www.us.apache.org/dist/spark/spark-2.0.2/spark-2.0.2-bin-hadoop2.7.tgz'
DOWNLOAD_LINK_SCALA='http://downloads.lightbend.com/scala/2.11.8/scala-2.11.8.deb'
DOWNLOAD_LINK_SBT='https://dl.bintray.com/sbt/native-packages/sbt/0.13.13/sbt-0.13.13.tgz'
DOWNLOAD_LINK_HADOOP='http://mirror.downloadvn.com/apache/hadoop/common/stable/hadoop-2.7.3.tar.gz'

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
    downloadFile $DOWNLOAD_LINK_SCALA scala.deb
    dpkg -i ./scala.deb
    scala -version
}

setupSpark(){
    printHeader 'Install spark'
    downloadFile $DOWNLOAD_LINK_SPARK spark.tar.gz
    mkdir $SETUP_DIR_SPARK 
    tar -xzf spark.tar.gz -C $SETUP_DIR_SPARK --strip-components 1

    tmpval="$PATH:$SETUP_DIR_SPARK/bin"
    setupEnvVar 'PATH' $tmpval  
}

setupSbt(){
    printMsg 'setupSbt';
    downloadFile $DOWNLOAD_LINK_SBT sbt.tar.gz
    mkdir $SETUP_DIR_SBT 
    tar -xzf sbt.tar.gz -C $SETUP_DIR_SBT --strip-components 1

    tmpval="$PATH:$SETUP_DIR_SBT/bin"
    setupEnvVar 'PATH' $tmpval

}

setupHadoop(){
    printMsg 'setupHadoop';
    downloadFile $DOWNLOAD_LINK_HADOOP hadoop.tar.gz
    mkdir $SETUP_DIR_HADOOP 
    tar -xzf hadoop.tar.gz -C $SETUP_DIR_SBT --strip-components 1

    tmpval="$PATH:$SETUP_DIR_HADOOP/bin"
    setupEnvVar 'PATH' $tmpval

    mkdir $SETUP_DIR_HADOOP/input
    cp $SETUP_DIR_HADOOP/etc/hadoop/*.xml $SETUP_DIR_HADOOP/input

    cat > $SETUP_DIR_HADOOP/etc/hadoop/core-site.xml << EOF
<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://localhost:9000</value>
    </property>
</configuration>
EOF
    cat > $SETUP_DIR_HADOOP/etc/hadoop/hdfs-site.xml << EOF
<configuration>
    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property>
</configuration>
EOF
    
}

setupSSHKey(){
    ssh-keygen -t rsa -P '' -f /home/ubuntu/.ssh/id_rsa
    cat ~/.ssh/id_rsa.pub >> /home/ubuntu/.ssh/authorized_keys
    chmod 0600 /home/ubuntu/.ssh/authorized_keys
}

setup () {
    printHeader 'SETUP SYSTEM'
    printMsg '- Create hadoop user'
    mkdir $SETUP_DIR_ROOT
    cd $SETUP_DIR_ROOT
    sudo adduser hadoop --gecos "Hadoop User,RoomNumber,WorkPhone,HomePhone" --disabled-password
    echo "hadoop:hadoop" | sudo chpasswd

    #SETUP
    setupSofts     
    setupScala
    setupSpark
    setupSbt
    setupHadoop

    #FILES, STUFFS
    printHeader 'RUN DEMO'
    downloadFile https://www.gutenberg.org/ebooks/1661.txt.utf-8 SherlockHolmes.txt
}

###########################################################################
# Run setup
###########################################################################
setup
