using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Schoopy.Data
{
    public class Blob
    {
        public string FileName { get; private set; }
        public byte[] Binary { get; private set; }

        public Blob(string fileName, byte[] binary)
        {
            this.FileName = fileName;
            this.Binary = binary;
        }

        public Blob(string FileName)
        {
            this.FileName = FileName;
        }

        public override string ToString()
        {
            return FileName;
        }

    }
}
