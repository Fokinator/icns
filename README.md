ICNS Utils
===
A pure and lightweight Java implementation of tools for working with [Apple Icon Image format](https://en.wikipedia.org/wiki/Apple_Icon_Image_format) (`.icns`) icons.

This is a fork of https://github.com/gino0631/icns (without the Maven ICNS generator plugin) that removes I/O operations 
where they are unnecessary/wasteful, fixes the architecture, adds missing record types and removes runtime dependencies.

## Key features
1. `IcnsIcons` - represents an ICNS object.
2. `IcnsRecord` - represents a single ICNS record (type + data).
3. `IcnsType` - represents an ICNS record type (icon, nested ICNS object, metadata, etc.).
4. `IcnsDecoder` - loads and parses ICNS objects.
5. `IcnsEncoder` - saves ICNS objects.


## To use

Add the dependency to your project:
* Maven:
```xml
<dependency>
    <groupId>net.fokinatorr</groupId>
    <artifactId>icns</artifactId>
    <version>0.1-beta</version>
</dependency>
```
* Gradle (Groovy DSL):
```groovy
implementation 'net.fokinatorr:icns:0.1-beta'
```

Also add this line to your `module-info.java`, if you have one:
```
requires net.fokinatorr.icns;
```
