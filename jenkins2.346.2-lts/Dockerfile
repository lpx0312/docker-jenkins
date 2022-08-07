FROM jenkins/jenkins:2.346.2-lts
# 先使用root用户下载软件
USER root
# 设置镜像的apt源
COPY --chown=root:root debian11.list /etc/apt/sources.list
# 设置pip源
COPY --chown=root:root pip.conf /root/.pip/pip.conf
# 设置公钥私钥
COPY --chown=root:root .ssh /root/.ssh
# 设置jenkins用户公钥私钥
COPY --chown=jenkins:jenkins .ssh /var/jenkins_home/.ssh
# 添加初始化脚本,用户提前设置用户名和密码
COPY --chown=jenkins:jenkins set_user_password.groovy /usr/share/jenkins/ref/init.groovy.d/set_user_password.groovy
# 更换下载源为 清华园，为下载插件做准备
COPY --chown=jenkins:jenkins plugins.txt  /usr/share/jenkins/ref/plugins.txt
ENV JENKINS_UC=https://mirrors.tuna.tsinghua.edu.cn/jenkins/updates
ENV JENKINS_UC_DOWNLOAD=https://mirrors.tuna.tsinghua.edu.cn/jenkins
ENV JENKINS_UC_EXPERIMENTAL=https://mirrors.tuna.tsinghua.edu.cn/jenkins/updates/experimental
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt

RUN apt-get update  \
        && apt-get install -y --no-install-recommends python3.9 python3.9-dev python3.9-venv \
        && ln -s /usr/bin/python3.9 /usr/local/bin/python3  \
        && ln -s /usr/bin/python3.9 /usr/local/bin/python  \
        && curl -s http://mirrors.lpx.com/soft/python/pip/get-pip3.py -o get-pip.py \
        && python3 get-pip.py  \
        && rm -f get-pip.py
RUN echo root:root | chpasswd
RUN pip install python-jenkins pyyaml requests
RUN apt-get install -y --no-install-recommends wget vim sshpass ansible inetutils-ping
USER jenkins
EXPOSE 8080 50000

# Djenkins.install.runSetupWizard=false 代表跳过jenkins初始化阶段
ENV JAVA_OPTS=-Djenkins.install.runSetupWizard=false -Duser.timezone=GMT+08
