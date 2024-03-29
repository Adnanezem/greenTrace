#! /bin/sh

mkdir -p ~/.m2/

if [ -f ~/.m2/settings.xml ]; then
    echo "File ~/.m2/settings.xml already exists. Please move it or remove it."
    exit 1
fi

cat  >~/.m2/settings.xml <<EOF 
<settings xmlns="http://maven.apache.org/SETTINGS/1.1.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.1.0 http://maven.apache.org/xsd/settings-1.1.0.xsd">
  <proxies>
EOF

NO_PROXY_MVN=$(printf '%s' "$NO_PROXY" | tr ',' '|')

for v in http_proxy FTP_PROXY ftp_proxy ALL_PROXY all_proxy HTTPS_PROXY https_proxy HTTP_PROXY
do
    url=$(eval printf '%s' \$$v)
    if [ -z "$url" ]; then
	continue
    fi
    proto=${v%_*}
    host=$(printf '%s' "$url" | sed 's/^.*:\/\/\([^:]*\):.*$/\1/g')
    port=$(printf '%s' "$url" | sed 's/^.*:\/\/[^:]*:\(.*\)$/\1/g')
    cat<<EOF >>~/.m2/settings.xml
   <proxy>
      <id>lyon1-$v</id>
      <active>true</active>
      <protocol>$proto</protocol>
      <host>$host</host>
      <port>$port</port>
      <nonProxyHosts>$NO_PROXY_MVN</nonProxyHosts>
    </proxy>
EOF
done

echo '  </proxies>' >>~/.m2/settings.xml


# Configurations de SonarQube
echo '  <!-- Configurations de SonarQube -->' >>~/.m2/settings.xml
echo '  <profiles>' >>~/.m2/settings.xml
echo '    <profile>' >>~/.m2/settings.xml
echo '      <id>sonar</id>' >>~/.m2/settings.xml
echo '      <properties>' >>~/.m2/settings.xml
echo '        <!-- URL du serveur SonarQube -->' >>~/.m2/settings.xml
echo '        <sonar.host.url>http://localhost:9000</sonar.host.url>' >>~/.m2/settings.xml
echo '        <!-- Token d acces pour SonarQube -->' >>~/.m2/settings.xml
echo '        <sonar.login>VOTRE_TOKEN</sonar.login>' >>~/.m2/settings.xml
echo '      </properties>' >>~/.m2/settings.xml
echo '    </profile>' >>~/.m2/settings.xml
echo '  </profiles>' >>~/.m2/settings.xml

echo '</settings>' >>~/.m2/settings.xml
