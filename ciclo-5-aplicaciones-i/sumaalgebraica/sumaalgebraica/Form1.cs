using System;
using System.Windows.Forms;

namespace sumaalgebraica
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void botonsuma_Click(object sender, EventArgs e)
        {
            // Usamos los nombres exactos que tienen tus cajas de texto
            double numero1 = Convert.ToDouble(textdato1.Text);
            double numero2 = Convert.ToDouble(textdato2.Text);

            double suma = numero1 + numero2;

            textResultado.Text = suma.ToString();
        }

        private void botonsuma_Click_1(object sender, EventArgs e)
        {
            double numero1 = Convert.ToDouble(textdato1.Text);
            double numero2 = Convert.ToDouble(textdato2.Text);
            double suma = numero1 + numero2;
            textResultado.Text = suma.ToString();
        }
    }
}