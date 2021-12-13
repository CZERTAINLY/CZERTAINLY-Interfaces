# CZERTAINLY Interfaces

> This repository is part of the commercial open source project CZERTAINLY. You can find more information about the project at [CZERTAINLY](https://github.com/3KeyCompany/CZERTAINLY) repository, including the contribution guide.

CZERTAINLY Interfaces is a set of interfaces related to the platform APIs and communication between the platform and available connectors. It contains description of the interfaces that can be treated as a reference for the developers and integrators.

These interface have to be generally applied when extending the platform, in case you would like to develop custom connectors providing some specific functionality or implementing some proprietary protocols.

## Connector functional group

Each connector must implement a specific functional group that defines the functionality provided by the connector. For more information on functional groups and types, refer to [CZERTAINLY documentation](https://docs.czertainly.com).

Functional groups can be combined, for example, a connector can implement both `Credential Provider` and `CA Connector` functional group, however types cannot be mixed.

Each connector has to implement the following interfaces:
- [Attributes](src/main/java/com/czertainly/api/interfaces/AttributesController.java)
- [Health](src/main/java/com/czertainly/api/interfaces/HealthController.java)
- [Info](src/main/java/com/czertainly/api/interfaces/InfoController.java)

CZERTAINLY supports the following connector functional groups:

| Functional group | Description | Additional interfaces needed to implement |
| ---------------- | ----------- | ---------------------------- |
| Credential Provider | Connector that provides a specific type of the credentials to the platform. Credential can be further used by the objects and other connector of the platform |  |
| Discovery Provider | Provides an interface to search for a certificates within a specific technology and sources | <ul><li>[Discovery](src/main/java/com/czertainly/api/interfaces/InfoController.java)</li></ul> |
| Legacy CA Connector | Provides a connection with the certification authority and certificate management functions | <ul><li>[Certificate](src/main/java/com/czertainly/api/interfaces/CertificateController.java)</li><li>[EndEntity](src/main/java/com/czertainly/api/interfaces/EndEntityController.java)</li><li>[EndEntityProfiles](src/main/java/com/czertainly/api/interfaces/EndEntityProfilesController.java)</li></ul> |
| CA Connector | Provides a connection with the certification authority and certificate management functions | <ul><li>[Certificate](src/main/java/com/czertainly/api/v2*interfaces/CertificateController.java)</li></ul> |

## Core interfaces

Core interfaces are the interfaces that are used by the platform to communicate with the connectors and the clients.

The core interfaces can be split into the following groups:
- Administration interface
- Client operations interface

You can find the detailed description of the interfaces in the following sections:
- [v1 interfaces](src/main/java/com/czertainly/api/core/interfaces)
- [v2 interfaces](src/main/java/com/czertainly/api/core/v2/interfaces)
