# mock-remote-config
Its important to consider different values of remote configs for running performance tests. Not considering remote config change while running performance test
can lead to miss catching regressions, especially which are due to different values of remote configs.

Hence for running performance benchmarks its important to also mock remote config values. This would help increase coverage of your performance tests to catch
regressions which can happen due to different values of remote configs. This repository shows how can we mock firebase remote config values for your performance tsts.

# Working

<img src="https://user-images.githubusercontent.com/12881364/185785426-66ec4a4f-1f4a-4164-8d45-860d0a24ae66.png" width=800px/>

1. Remote config resolves the values from network, packaged defaults or statically and stores in frc activated json in app file cache.
2. We can mock this file to test the code with different values.
3. With help of [Firebase Remote Config Api](https://firebase.google.com/docs/reference/remote-config/rest) we can get all conditions on remote 
4. For all conditions we can generate activated json with parameter values resolving for that condition
5. These activated json files can then be pushed to app file cache before running any performance test
6. This will ensure that you are testing for all possible conditional values on remote

**DISCLAIMER**: This assumes that you are not running performance test with real network which should not be the case because it can be source of noise
for your performance benchmarks.

# Running the project

1. You need to generate service account json file and save it in your local from firebase console to get access token
2. Create secrets.json and save project id in it.
```
{
  "project_id": "<your-project-id-here>"
}
```
3. Run `./gradlew fatJar`
4. Generate mocked configs for each condition by executing following command

```
 java -jar ./build/libs/MockRemoteConfig.jar --appId <package-id> --secret <path-to-secrets-file> --serviceAccount <path-to-service-account>
```




