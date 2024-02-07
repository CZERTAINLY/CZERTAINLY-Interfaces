# CZERTAINLY Interfaces

> This repository is part of the open source project CZERTAINLY. You can find more information about the project at [CZERTAINLY](https://github.com/3KeyCompany/CZERTAINLY) repository, including the contribution guide.

CZERTAINLY Interfaces is a set of interfaces related to the platform APIs and communication between the platform and available `Connectors`. It contains description of the interfaces that can be treated as a reference for the developers and integrators.

These interface have to be generally applied when extending the platform, in case you would like to develop custom `Connectors` providing some specific functionality or implementing some proprietary protocols.

## Connector Function Group

Each `Connector` must implement a specific `Function Group` that defines the functionality provided by the `Connector`. For more information on `Function Groups` and `Kinds`, refer to [CZERTAINLY documentation](https://docs.czertainly.com).

`Function Groups` can be combined, for example, a `Connector` can implement both `Credential Provider` and `Authority Provider` `Function Group`, however `Kinds` cannot be mixed.

Each `Connector` has to implement the following interfaces:
- [Attributes](src/main/java/com/czertainly/api/interfaces/connector/AttributesController.java)
- [Health](src/main/java/com/czertainly/api/interfaces/connector/HealthController.java)
- [Info](src/main/java/com/czertainly/api/interfaces/connector/InfoController.java)

CZERTAINLY supports the following `Connector` `Function Groups`:

| Function Group              | Description                                                                                                                                                           | Additional interfaces needed to implement                                                                                                                                                                                                                                                                                                                                                                |
|-----------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `Credential Provider`       | `Connector` that provides a specific type of the `Credentials` to the platform. `Credential` can be further used by the objects and other `Connector` of the platform |                                                                                                                                                                                                                                                                                                                                                                                                          |
| `Discovery Provider`        | Provides an interface to search for a certificates within a specific technology and sources                                                                           | <ul><li>[Discovery](src/main/java/com/czertainly/api/interfaces/connector/DiscoveryController.java)</li></ul>                                                                                                                                                                                                                                                                                            |
| `Legacy Authority Provider` | Provides a connection with the certification authority and certificate management functions                                                                           | <ul><li>[Certificate](src/main/java/com/czertainly/api/interfaces/connector/CertificateController.java)</li><li>[EndEntity](src/main/java/com/czertainly/api/interfaces/connector/EndEntityController.java)</li><li>[EndEntityProfiles](src/main/java/com/czertainly/api/interfaces/connector/EndEntityProfilesController.java)</li></ul>                                                                |
| `v2 Authority Provider`     | Provides a connection with the certification authority and certificate management functions                                                                           | <ul><li>[Certificate](src/main/java/com/czertainly/api/interfaces/connector/v2/CertificateController.java)</li></ul>                                                                                                                                                                                                                                                                                     |
| `Entity Provider`           | Provides the functionality to push the certificate to entities, discover certificates from entities                                                                   | <ul><li>[Entity](src/main/java/com/czertainly/api/interfaces/connector/entity/EntityController.java)</li><li>[Location](src/main/java/com/czertainly/api/interfaces/connector/entity/LocationController.java)</li></ul>                                                                                                                                                                                  |
| `Compliance Provider`       | Provides functionality to check the compliance of certificates based on rules                                                                                         | <ul><li>[Compliance](src/main/java/com/czertainly/api/interfaces/connector/ComplianceController.java)</li><li>[Compliance Rules](src/main/java/com/czertainly/api/interfaces/connector/ComplianceRulesController.java)</li></ul>                                                                                                                                                                         |
| `Cryptography Provider`     | Provides cryptographic key management and operations                                                                                                                  | <ul><li>[TokenInstance](src/main/java/com/czertainly/api/interfaces/connector/cryptography/TokenInstanceController.java)</li><li>[KeyManagement](src/main/java/com/czertainly/api/interfaces/connector/cryptography/KeyManagementController.java)</li><li>[CryptographicOperations](src/main/java/com/czertainly/api/interfaces/connector/cryptography/CryptographicOperationsController.java)</li></ul> |

## Core interfaces

`Core` interfaces are the interfaces that are used by the platform to communicate with the `Connectors` and the clients.

You can find the detailed description of the interfaces in the following sections:
- [Core interfaces](src/main/java/com/czertainly/api/interfaces/core)

## Protocol interfaces

`Protocol` interfaces can be used to create a custom implementation and behaviour for standard certificate management protocols.

The following interfaces are available:

- [ACME interface](src/main/java/com/czertainly/api/interfaces/core/acme/AcmeController.java)
- [RA Profile ACME interface](src/main/java/com/czertainly/api/interfaces/core/acme/AcmeRaProfileController.java)

- [SCEP interface](src/main/java/com/czertainly/api/interfaces/core/scep/ScepController.java)
- [RA Profile SCEP interface](src/main/java/com/czertainly/api/interfaces/core/scep/ScepRaProfileController.java)
