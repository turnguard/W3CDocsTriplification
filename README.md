# W3CDocsTriplification

1. Checkout the various folders in src/test/resources/w3c. They will include a downloaded version of the w3c-document (these will most probably contain changes that are necessary so the doc can be parsed) and the corresponding xslt stylesheet along with other files that might be necessary.
2. Junit test "W3CTransformations" (in src/test/java/...) will run all available transformations. It will output derived rdf datasets into target/rdf-output
3. The xslt stylesheets can be used/changed to your liking
