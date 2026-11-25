namespace sumaalgebraica
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            label1 = new Label();
            label2 = new Label();
            label3 = new Label();
            textdato1 = new TextBox();
            textdato2 = new TextBox();
            textResultado = new TextBox();
            botonsuma = new Button();
            SuspendLayout();
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(380, 131);
            label1.Name = "label1";
            label1.Size = new Size(37, 15);
            label1.TabIndex = 0;
            label1.Text = "dato1";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Location = new Point(379, 175);
            label2.Name = "label2";
            label2.Size = new Size(37, 15);
            label2.TabIndex = 1;
            label2.Text = "dato2";
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Location = new Point(378, 270);
            label3.Name = "label3";
            label3.Size = new Size(56, 15);
            label3.TabIndex = 2;
            label3.Text = "resultado";
            // 
            // textdato1
            // 
            textdato1.Location = new Point(437, 123);
            textdato1.Name = "textdato1";
            textdato1.Size = new Size(100, 23);
            textdato1.TabIndex = 3;
            // 
            // textdato2
            // 
            textdato2.Location = new Point(435, 167);
            textdato2.Name = "textdato2";
            textdato2.Size = new Size(100, 23);
            textdato2.TabIndex = 4;
            // 
            // textResultado
            // 
            textResultado.Location = new Point(435, 267);
            textResultado.Name = "textResultado";
            textResultado.Size = new Size(100, 23);
            textResultado.TabIndex = 5;
            // 
            // botonsuma
            // 
            botonsuma.Location = new Point(435, 218);
            botonsuma.Name = "botonsuma";
            botonsuma.Size = new Size(75, 23);
            botonsuma.TabIndex = 6;
            botonsuma.Text = "sumar";
            botonsuma.UseVisualStyleBackColor = true;
            botonsuma.Click += botonsuma_Click_1;
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(800, 450);
            Controls.Add(botonsuma);
            Controls.Add(textResultado);
            Controls.Add(textdato2);
            Controls.Add(textdato1);
            Controls.Add(label3);
            Controls.Add(label2);
            Controls.Add(label1);
            Name = "Form1";
            Text = "Form1";
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private Label label1;
        private Label label2;
        private Label label3;
        private TextBox textdato1;
        private TextBox textdato2;
        private TextBox textResultado;
        private Button botonsuma;
    }
}
