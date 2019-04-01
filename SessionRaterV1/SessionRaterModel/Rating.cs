using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SessionRaterModel
{
    public class Rating
    {
        public int RatingId { get; private set; }
        public int RatingValue { get; private set; }
        public string Evaluator { get; private set; }
        public Session EvaluatedSession { get; set; }
        private static int nextRatingId=0;

        public Rating(int value, string evaluator)
        {
            this.RatingId = nextRatingId++;
            this.RatingValue = value;
            this.Evaluator = evaluator;

        }
    }
}