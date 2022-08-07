#!groovy
import hudson.security.*
import jenkins.model.*

def instance = Jenkins.getInstance()
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
def users = hudsonRealm.getAllUsers()
users_s = users.collect { it.toString() }

// Create the admin user account if it doesn't already exist.
if ("admin" in users_s) {
    println "Admin user already exists - updating password"

    def user = hudson.model.User.get('admin');
    def password = hudson.security.HudsonPrivateSecurityRealm.Details.fromPlainPassword('Lipanxiang1102')
    user.addProperty(password)
    user.save()
}
else {
    println "--> creating local admin user"

    hudsonRealm.createAccount('admin', 'Lipanxiang1102')
    instance.setSecurityRealm(hudsonRealm)

    def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
    instance.setAuthorizationStrategy(strategy)
    instance.save()
}


// update  不使用这个了
//println "--> Update Plugin Center."
//Jenkins j = Jenkins.getInstance()
//j.pluginManager.doSiteConfigure("https://mirrors.tuna.tsinghua.edu.cn/jenkins/updates/update-center.json")
//j.pluginManager.doCheckUpdatesServer()