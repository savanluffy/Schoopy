using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SessionRaterModel
{
    public class Session
    {
        public int Id { get; private set; }
        public string Title { get; private set; }
        public string Speaker { get; private set; }

        public SessionState CurrentSessionState { get; set; }

        public HashSet<Rating> Ratings { get; private set; }

        public Session(int id, string title, string speaker)
        {
            this.Id = id;
            this.Title = title;
            this.Speaker = speaker;
            this.Ratings = new HashSet<Rating>();
        }

        public double getAverageRating()
        {
            double result=0;
            int count = 0;
            
            foreach(Rating currentRating in Ratings)
            {
                result = result+currentRating.RatingValue;
                count++;
            }

            if (result != 0)
            {
                result = result / count;
            }
            return result;
        }
    }
}