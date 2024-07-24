# Todo

1. Implement rate limiting to login page or other sensitive pages, so that hacking user accounts becomes harder.

2. Perhaps adding an "is_active" field to the "Account" entity?

3. Implement exception handling for invalid URLs, for example, when a user requests something from a URL that doesn't exist.

4. Documentation as well as adding comments like these above classes/methods in the project:

    ```Java
   /**
   * @param tagID - ID of the tag
   * @param startDate - Starting Date
   * @param endDate - End date
   * @param estimated <-- this is not the param - should be removed or fix doc
   * @return <-- missing return param and description
   * @throws ServerException -- throws server exception
   */
   ```


## Entity design


1. https://medium.com/@bubu.tripathy/best-practices-entity-class-design-with-jpa-and-spring-boot-6f703339ab3d


## Responses


1. https://medium.com/@aedemirsen/generic-api-response-with-spring-boot-175434952086


## Generic


1. https://medium.com/spring-boot/improve-your-spring-boot-code-readability-by-237-17ca97f1d2e4


## Other


1. _It might look like a trivial mistake, but in my experience, I see it too many times in
applications—never use HTTP GET with mutating operations! Do not implement behavior that
changes data and allows it to be called with an HTTP GET endpoint. Remember that calls to HTTP
GET endpoints don't require a CSRF token._
