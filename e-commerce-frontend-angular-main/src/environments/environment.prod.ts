// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: true,
  title: 'Production Environment Heading',
  withCredentials: true,
  baseUrl: "http://ecommerceshroosterbackendapi-env.eba-kqdcw2sm.us-east-1.elasticbeanstalk.com/api",
  headers: {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': 'http://e-commerce-shrooster.s3-website-us-east-1.amazonaws.com/',
    'rolodex-token': `${window.sessionStorage.getItem('token')}`
  },
};



/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
