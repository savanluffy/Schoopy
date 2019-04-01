using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SessionRaterModel
{
    public static class SessionManager
    {
        static List<Session> sessions = new List<Session>();
        static int nextId = 1;

        public static Session CreateSession(string title, string speaker)
        {
            // Parameter überprüfen 
            if(title.Length ==0 || speaker.Length == 0)
            {
                throw new Exception("Title or speaker is missing");
            }

            // Eindeutigkeit des Titels sicherstellen - sonst Exception
            foreach(Session currentSession in sessions)
            {
                if (currentSession.Title.Equals(title))
                {
                    throw new Exception("This title already exists");
                }
            }
            Session result = new Session(nextId,title,speaker);
            nextId++;
            result.CurrentSessionState = SessionState.Created;
            sessions.Add(result);

            // belegem und einhängen (in Liste)

            return result;

        }

        public static void RateSession(int sessionId, string evaluator, int ratingValue)
        {
            Session currentSession = Get(sessionId);

            if (evaluator.Length == 0)
            {
                throw new Exception("Evaluator is missing.");
            }
            if (ratingValue < 0 || ratingValue > 10)
            {
                throw new Exception("Rating value is not valid. ");
            }
            foreach(Rating currentRating in currentSession.Ratings)
            {
                if (currentRating.Evaluator.Equals(evaluator))
                {
                    throw new Exception("This evaluator has already rated!");
                }
            }
            
            currentSession.CurrentSessionState = SessionState.InEvaluation;

            Rating newRating = new Rating(ratingValue,evaluator);
            currentSession.Ratings.Add(newRating);

            currentSession.CurrentSessionState = SessionState.Evaluated;
            newRating.EvaluatedSession = currentSession;
        }

        public static Session Delete(int sessionId)
        {
            Session result = Get(sessionId);
            sessions.Remove(result);
            return result;
        }

        public static Session Get(int sessionId)
        {
            Session result = null;
            foreach (Session currentSession in sessions)
            {
                if (currentSession.Id == sessionId)
                {
                    result = currentSession;
                    break;
                }
            }
            if (result == null)
            {
                throw new Exception("This session doesn't exists!!");
            }

            return result;
        }

        public static List<Session> Get()
        {
            if (sessions.Capacity == 0)
            {
                throw new Exception("The list is empty!");
            }
            return new List<Session>(sessions);
        }

        public static void CloseSession(int sessionId)
        {
            CloseSession(Get(sessionId));
        }

        public static void CloseSession(Session sessionToClose)
        {
            if (sessionToClose.CurrentSessionState != SessionState.Evaluated)
            {
                throw new Exception("You can't close an unevaluated session!");
            }

            sessionToClose.CurrentSessionState = SessionState.Closed;
        }
    }
}