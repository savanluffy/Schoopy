using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Schoopy.Data
{
    public class LocalDate
    {
        public int year { get; set; }
        public int month { get; set; }
        public int day { get; set; }

        public LocalDate(int _year, int _month, int _day)
        {
            this.year = _year;
            this.month = _month;
            this.day = _day;
        }

        public LocalDate() { }

    }
}
