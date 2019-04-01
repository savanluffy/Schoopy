"use strict";

var sessionManager = (function () {
    var collSessions = [];
    var nextSId = 1;
    var nextRId = 1;
    var SessionState = {
        CREATED: { value: 0, name: "Created", code: "Cr" },
        EVALUATED: { value: 1, name: "Evaluated", code: "E" },
        CLOSED: { value: 2, name: "Closed", code: "Cl" }
    };

    return {
        createTestData: _createTestData,
        createSession: _createSessionInternal,
        getSession: _getSessionInternal,
        getSessions: _getSessionsInternal,
        deleteSession: _deleteSessionInternal
    };
  
    function _createSessionInternal(title, speaker) {
        var newSession =new Session(title, speaker);
        _alreadyInColl(newSession);
        collSessions.push(newSession);
    }
    function _getSessionInternal(sessionId) {
        var result = null;
        for (var i = 0; i < collSessions.length; i++) {
            if (sessionId == collSessions[i].Id) {
                result = collSessions[i];
            }
        }
        return result;
    }
    function _getSessionsInternal() {
        var SessionClone = [];

        if (collSessions.length == 0) {
            throw "No sessions created";
        }
        for (var i = 0; i < collSessions.length; i++){
            SessionClone.push(collSessions[i]);
        }
        return SessionClone;
    }
    function _createTestData() {
        _createSessionInternal("UML", "Mueller");
        _createSessionInternal("BSD", "Karasek");
        _createSessionInternal("POS", "Ortner");
        _createSessionInternal("BWM", "Gitschtaler");
        _createSessionInternal("NVS", "Oberlercher");
    }
    function _deleteSessionInternal(sessionId) {
        return collSessions.splice(sessionId-1,1);
    }

    function _isValidString(data) {
        if (typeof (data) != "string") {
            throw "This is not a string: " + data;
        }
        return data;
    }
    function _alreadyInColl(validSession) {
        if (collSessions.some(function (e) { return e.Title === validSession.Title }))
            throw "This title is already used!";
    }
    function Session(title, speaker) {
        this.Id = nextSId++;
        this.Title = _isValidString(title);
        this.Speaker = _isValidString(speaker);
        this.SessionState = SessionState.CREATED;
        this.Ratings = [];
    }
    function Rating(rValue, evaluator) {
        this.RatingId = nextRId++;
        this.RatingValue = rValue;
        this.Evaluator = evaluator;
        this.EvaluatedSession = evaluatedSession;
    }
})();
module.exports=sessionManager;