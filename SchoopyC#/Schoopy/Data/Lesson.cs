using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Schoopy
{

    public enum Weekday { MONDAY, TUESDAY, WEDNESDAY,THURSDAY,FRIDAY }
    public class Lesson
    {
        public Room  schoolRoom{ get; set; }
        public Room teachingRoom { get; set; }
        public TeacherSpecialization teachinginfo { get; set; }
        public Weekday weekday { get; set; }

        public int schoolHour { get; set; }

        public override string ToString()
        {
            return this.teachinginfo.subject.subjectName ;
        }
    }
}
