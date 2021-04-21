/**
Author: CHSH
Created: 20-APR-2018
Description: Get the database configuration for the enviroment.  
Parameters: Class designed to compile pl/sql for Oracle in Jenkins
Scope: Jenkins Operations - Oracle get EBS configurations.
**/
def call(String ebsName, String configType ) {
  echo "Accessing configuration " + configType + "  for " + ebsName
  // setting default if nothing is found.
  String dbConfig = ""
  
  configFileProvider([configFile(fileId: "EBSInstances.json", variable: 'EBS_SETTINGS')]) {
    def config = readJSON file: "$EBS_SETTINGS"
    def branch_config = config."${ebsName}"

    if (branch_config) {
      echo "Getting ${configType} for branch ${ebsName} from EBSInstances.json in Jenkins."
    } else {
      echo "\u001B[33m” Unable to find configuration for ${ebsName} from EBSInstances.json in Jenkins.  Using default configuration. \u001B[0m"
      branch_config = config.default
    }

    dbConfig = branch_config.get(configType)
  }

  echo "Selected " + dbConfig
  return dbConfig
} 
