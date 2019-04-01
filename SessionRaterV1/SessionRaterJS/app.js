var sessionManager = require("./SessionRaterBL.js");
try {
    console.log("<<<<<<<<<<<<<<<<<<<<<<<<Create Sessions>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    sessionManager.createTestData();
    console.log("Sessions Created\n");
    console.log("<<<<<<<<<<<<<<<<<<<<<<<<Get particular Session>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    console.log("\n");
    console.log(sessionManager.getSession(3));
    console.log("\n");
    console.log(sessionManager.getSession(1));
    console.log("\n");
    console.log(sessionManager.getSession(4));
    console.log("\n");
    console.log("<<<<<<<<<<<<<<<<<<<<<<<<Delete particular Session>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    console.log("\n");
    console.log(sessionManager.deleteSession(1));
    console.log("\n");
    console.log("<<<<<<<<<<<<<<<<<<<<<<<<Get all Sessions>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    console.log("\n");
    console.log(sessionManager.getSessions());
} catch (ex) {
    console.log(ex);
}
