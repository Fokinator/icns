/**
 * A pure Java implementation of tools for working with Apple Icon Image format ({@code .icns}) icons.
 */
module net.fokinatorr.icns {
    requires static lombok;

    exports net.fokinatorr.icns;
    exports net.fokinatorr.icns.records;
    exports net.fokinatorr.icns.io;
    exports net.fokinatorr.icns.type;
}