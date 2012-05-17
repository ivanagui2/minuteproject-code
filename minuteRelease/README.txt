Todo list to release

1) change version number where necessary
- minuteKernel
- minuteFoundation
- minuteTargets * target number
- update pom.xml => for new release x.y.z
change version in install_maven (target/mp-bsla/application/dist)

1)2) Release note update

2) Upgrade feature
2) Perform merge between MinuteKernel/config and minuteDemo/demo/config
- This is to upgrade the new features (tracks and templates)

2)2) Catalog
- add tracks in catalog
- add db in database-catalog

3) Release
3)1) Test
- Run unit test
- Run Fitnesse suite

3)2) Build
In MinuteprojectRelease run
>ant

3)3) Validate Demo update the demo
- update template config
- run demo 
-- all-demo.cmd/sh all-packages.cmd
-- on windows and linux
-- deploy new tracks

4) Source control
- Commit
- tag

5) Publish
- sourceforge release

