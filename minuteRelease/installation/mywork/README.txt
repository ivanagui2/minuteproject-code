To customize your generation you can processed the following:
* from /demo/config/ copy into /mywork/config/ the files corresponding to the technology your are interested to develop with .
  In other word if you interest to develop with spring-hibernate copy
  * demo-hibernate.cmd
  * mp-config-hibernate.xml
  * mp-template-hibernate.xml
  +
  * lcp.cmd
  
1) adapt the name of the file to your need
  * demo-xx.cmd can be change to anyother cmd file
    inside it refers to mp-config-xx.xml 
  * If you change mp-config-xx.xml , adapt the last line of your (renamed) demo-xx.cmd
  * If you change mp-template-xx.xml, adapt the line in your (renamed) mp-config-xx.xml
  
2) customize the content
2)1) in your (renamed) mp-config-xx.xml
  * Adapt DB connection parameter 
    * add the correct jdbc driver class corresponding to your database (check that the jar is in /application/lib/extra)
    * if your using DB2 or Oracle please add the tag schema (uncomment) and set your schema where the tables/views are
  * Set primary key policy:
    * Currently MinuteProject support primary key policy based on sequence
      * If you want the primary key to be based on the name of the table (safe for m2m table and natural pk)
        * in the node primaryKeyPolicy set oneGlobal="false" oneForEachTable="true"
        * in the node primaryKeyPolicyPattern set name="sequencePattern" and choose the prefix of suffix you wish (by default it appends _SEQ to the name of the table)
  * Adapt the name of the model in 
    <configuration>
     <model name="<set your business name>"/>
     ...
    </configuration>
  * Generation condition
    * if you want to exclude some table form the build
      * in the node business-model add the node generation-condition add a node condition :
       <business-model>
        <generation-condition>
         <condition type="exclude" startsWith="TEM_">
        </generation-condition>
        ...
       </business-model>  
        It will not generate for table starting with TEM_
  * Set a business package to some table (Ex: tables starting with AD_ goes in package 'admin', tables starting with FIN_ goes in finance...)
       <business-model>
        <business-package>
         <condition type="package" startsWith="AD_" result="admin">
         <condition type="package" startsWith="FIN_" result="finance">
        </business-package>
        ...
       </business-model>
        
     
      