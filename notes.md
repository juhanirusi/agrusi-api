# Notes To Help With Development Decisions...


1. You don't always need to implement both PUT and PATCH methods. The choice depends on your APIs requirements and use cases. Here are some considerations:

   - Implement only PUT when:

     - Your resources are small and simple.
     - Full updates are the norm in your application.
     - You want to enforce that all fields are always provided.

   - Implement only PATCH when:

     - Partial updates are common and full updates are rare.
     - Your resources are large, and you want to optimize network usage.

   - Implement both when:

     - You have complex resources where both full and partial updates make sense.
     - You want to provide maximum flexibility to API consumers.
     - You're building a public API where different clients might have different needs.

In many cases, implementing both provides the most flexibility, but it's not always necessary.

In the UI, use the data-driven approach and compare the data to be sent with the original data.

   - If all fields are present and modified, use PUT.
   - If only some fields are modified, use PATCH.
