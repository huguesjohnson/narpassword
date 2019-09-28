namespace narpaswin
{
    partial class MainForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components=null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if(disposing&&(components!=null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            this.labelPassPhrase = new System.Windows.Forms.Label();
            this.textBoxPassPhrase = new System.Windows.Forms.TextBox();
            this.labelPassPhraseHint = new System.Windows.Forms.Label();
            this.labelPasswordName = new System.Windows.Forms.Label();
            this.labelPasswordNameHint = new System.Windows.Forms.Label();
            this.labelPassword = new System.Windows.Forms.Label();
            this.textBoxPassword = new System.Windows.Forms.TextBox();
            this.groupBoxOptions = new System.Windows.Forms.GroupBox();
            this.labelPasswordLength = new System.Windows.Forms.Label();
            this.trackBarPasswordLength = new System.Windows.Forms.TrackBar();
            this.checkBoxUseSpecialCharacters = new System.Windows.Forms.CheckBox();
            this.checkBoxUseNumbers = new System.Windows.Forms.CheckBox();
            this.checkBoxUseUCase = new System.Windows.Forms.CheckBox();
            this.checkBoxUseLCase = new System.Windows.Forms.CheckBox();
            this.buttonSave = new System.Windows.Forms.Button();
            this.toolTipPasswordLength = new System.Windows.Forms.ToolTip(this.components);
            this.buttonCopyToClipboard = new System.Windows.Forms.Button();
            this.imageListButtons = new System.Windows.Forms.ImageList(this.components);
            this.labelCopyright = new System.Windows.Forms.Label();
            this.linkLabelHJ = new System.Windows.Forms.LinkLabel();
            this.labelGpl = new System.Windows.Forms.Label();
            this.comboBoxPasswordName = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.checkBoxClearOnMinimize = new System.Windows.Forms.CheckBox();
            this.checkBoxClearOnInactivity = new System.Windows.Forms.CheckBox();
            this.textBoxSeconds = new System.Windows.Forms.TextBox();
            this.labelSecondsOfInactivity = new System.Windows.Forms.Label();
            this.timer = new System.Windows.Forms.Timer(this.components);
            this.groupBoxOptions.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.trackBarPasswordLength)).BeginInit();
            this.SuspendLayout();
            // 
            // labelPassPhrase
            // 
            this.labelPassPhrase.AutoSize = true;
            this.labelPassPhrase.Location = new System.Drawing.Point(42, 24);
            this.labelPassPhrase.Name = "labelPassPhrase";
            this.labelPassPhrase.Size = new System.Drawing.Size(69, 13);
            this.labelPassPhrase.TabIndex = 0;
            this.labelPassPhrase.Text = "Pass Phrase:";
            // 
            // textBoxPassPhrase
            // 
            this.textBoxPassPhrase.Location = new System.Drawing.Point(117, 21);
            this.textBoxPassPhrase.Name = "textBoxPassPhrase";
            this.textBoxPassPhrase.PasswordChar = '*';
            this.textBoxPassPhrase.Size = new System.Drawing.Size(251, 20);
            this.textBoxPassPhrase.TabIndex = 1;
            this.textBoxPassPhrase.TextChanged += new System.EventHandler(this.textBoxPassPhrase_TextChanged);
            // 
            // labelPassPhraseHint
            // 
            this.labelPassPhraseHint.AutoSize = true;
            this.labelPassPhraseHint.ForeColor = System.Drawing.SystemColors.InactiveCaptionText;
            this.labelPassPhraseHint.Location = new System.Drawing.Point(123, 44);
            this.labelPassPhraseHint.Name = "labelPassPhraseHint";
            this.labelPassPhraseHint.Size = new System.Drawing.Size(248, 13);
            this.labelPassPhraseHint.TabIndex = 3;
            this.labelPassPhraseHint.Text = "i.e. \"I like pie\" or \"Please no more California songs\"";
            // 
            // labelPasswordName
            // 
            this.labelPasswordName.AutoSize = true;
            this.labelPasswordName.Location = new System.Drawing.Point(26, 75);
            this.labelPasswordName.Name = "labelPasswordName";
            this.labelPasswordName.Size = new System.Drawing.Size(87, 13);
            this.labelPasswordName.TabIndex = 4;
            this.labelPasswordName.Text = "Password Name:";
            // 
            // labelPasswordNameHint
            // 
            this.labelPasswordNameHint.AutoSize = true;
            this.labelPasswordNameHint.ForeColor = System.Drawing.SystemColors.InactiveCaptionText;
            this.labelPasswordNameHint.Location = new System.Drawing.Point(123, 95);
            this.labelPasswordNameHint.Name = "labelPasswordNameHint";
            this.labelPasswordNameHint.Size = new System.Drawing.Size(211, 13);
            this.labelPasswordNameHint.TabIndex = 7;
            this.labelPasswordNameHint.Text = "i.e. \"Facebook\" or \"yourname@gmail.com\"";
            // 
            // labelPassword
            // 
            this.labelPassword.AutoSize = true;
            this.labelPassword.Location = new System.Drawing.Point(55, 131);
            this.labelPassword.Name = "labelPassword";
            this.labelPassword.Size = new System.Drawing.Size(56, 13);
            this.labelPassword.TabIndex = 8;
            this.labelPassword.Text = "Password:";
            // 
            // textBoxPassword
            // 
            this.textBoxPassword.HideSelection = false;
            this.textBoxPassword.Location = new System.Drawing.Point(117, 128);
            this.textBoxPassword.Name = "textBoxPassword";
            this.textBoxPassword.ReadOnly = true;
            this.textBoxPassword.Size = new System.Drawing.Size(230, 20);
            this.textBoxPassword.TabIndex = 9;
            // 
            // groupBoxOptions
            // 
            this.groupBoxOptions.Controls.Add(this.labelPasswordLength);
            this.groupBoxOptions.Controls.Add(this.trackBarPasswordLength);
            this.groupBoxOptions.Controls.Add(this.checkBoxUseSpecialCharacters);
            this.groupBoxOptions.Controls.Add(this.checkBoxUseNumbers);
            this.groupBoxOptions.Controls.Add(this.checkBoxUseUCase);
            this.groupBoxOptions.Controls.Add(this.checkBoxUseLCase);
            this.groupBoxOptions.Location = new System.Drawing.Point(29, 166);
            this.groupBoxOptions.Name = "groupBoxOptions";
            this.groupBoxOptions.Size = new System.Drawing.Size(339, 166);
            this.groupBoxOptions.TabIndex = 11;
            this.groupBoxOptions.TabStop = false;
            this.groupBoxOptions.Text = "Password Options";
            // 
            // labelPasswordLength
            // 
            this.labelPasswordLength.AutoSize = true;
            this.labelPasswordLength.Location = new System.Drawing.Point(13, 115);
            this.labelPasswordLength.Name = "labelPasswordLength";
            this.labelPasswordLength.Size = new System.Drawing.Size(92, 13);
            this.labelPasswordLength.TabIndex = 16;
            this.labelPasswordLength.Text = "Password Length:";
            // 
            // trackBarPasswordLength
            // 
            this.trackBarPasswordLength.LargeChange = 16;
            this.trackBarPasswordLength.Location = new System.Drawing.Point(111, 115);
            this.trackBarPasswordLength.Maximum = 16;
            this.trackBarPasswordLength.Minimum = 1;
            this.trackBarPasswordLength.Name = "trackBarPasswordLength";
            this.trackBarPasswordLength.Size = new System.Drawing.Size(222, 45);
            this.trackBarPasswordLength.SmallChange = 8;
            this.trackBarPasswordLength.TabIndex = 17;
            this.trackBarPasswordLength.Value = 4;
            this.trackBarPasswordLength.Scroll += new System.EventHandler(this.trackBarPasswordLength_Scroll);
            this.trackBarPasswordLength.ValueChanged += new System.EventHandler(this.trackBarPasswordLength_ValueChanged);
            // 
            // checkBoxUseSpecialCharacters
            // 
            this.checkBoxUseSpecialCharacters.AutoSize = true;
            this.checkBoxUseSpecialCharacters.Checked = true;
            this.checkBoxUseSpecialCharacters.CheckState = System.Windows.Forms.CheckState.Checked;
            this.checkBoxUseSpecialCharacters.Location = new System.Drawing.Point(16, 92);
            this.checkBoxUseSpecialCharacters.Name = "checkBoxUseSpecialCharacters";
            this.checkBoxUseSpecialCharacters.Size = new System.Drawing.Size(221, 17);
            this.checkBoxUseSpecialCharacters.TabIndex = 15;
            this.checkBoxUseSpecialCharacters.Text = "Use special characters (!@#$%^&*-=+:;?,.)";
            this.checkBoxUseSpecialCharacters.UseVisualStyleBackColor = true;
            this.checkBoxUseSpecialCharacters.CheckedChanged += new System.EventHandler(this.checkBoxUseSpecialCharacters_CheckedChanged);
            // 
            // checkBoxUseNumbers
            // 
            this.checkBoxUseNumbers.AutoSize = true;
            this.checkBoxUseNumbers.Checked = true;
            this.checkBoxUseNumbers.CheckState = System.Windows.Forms.CheckState.Checked;
            this.checkBoxUseNumbers.Location = new System.Drawing.Point(16, 69);
            this.checkBoxUseNumbers.Name = "checkBoxUseNumbers";
            this.checkBoxUseNumbers.Size = new System.Drawing.Size(112, 17);
            this.checkBoxUseNumbers.TabIndex = 14;
            this.checkBoxUseNumbers.Text = "Use numbers (0-9)";
            this.checkBoxUseNumbers.UseVisualStyleBackColor = true;
            this.checkBoxUseNumbers.CheckedChanged += new System.EventHandler(this.checkBoxUseNumbers_CheckedChanged);
            // 
            // checkBoxUseUCase
            // 
            this.checkBoxUseUCase.AutoSize = true;
            this.checkBoxUseUCase.Checked = true;
            this.checkBoxUseUCase.CheckState = System.Windows.Forms.CheckState.Checked;
            this.checkBoxUseUCase.Location = new System.Drawing.Point(16, 46);
            this.checkBoxUseUCase.Name = "checkBoxUseUCase";
            this.checkBoxUseUCase.Size = new System.Drawing.Size(180, 17);
            this.checkBoxUseUCase.TabIndex = 13;
            this.checkBoxUseUCase.Text = "Use upper case characters (A-Z)";
            this.checkBoxUseUCase.UseVisualStyleBackColor = true;
            this.checkBoxUseUCase.CheckedChanged += new System.EventHandler(this.checkBoxUseUCase_CheckedChanged);
            // 
            // checkBoxUseLCase
            // 
            this.checkBoxUseLCase.AutoSize = true;
            this.checkBoxUseLCase.Checked = true;
            this.checkBoxUseLCase.CheckState = System.Windows.Forms.CheckState.Checked;
            this.checkBoxUseLCase.Location = new System.Drawing.Point(16, 23);
            this.checkBoxUseLCase.Name = "checkBoxUseLCase";
            this.checkBoxUseLCase.Size = new System.Drawing.Size(175, 17);
            this.checkBoxUseLCase.TabIndex = 12;
            this.checkBoxUseLCase.Text = "Use lower case characters (a-z)";
            this.checkBoxUseLCase.UseVisualStyleBackColor = true;
            this.checkBoxUseLCase.CheckedChanged += new System.EventHandler(this.checkBoxUseLCase_CheckedChanged);
            // 
            // buttonSave
            // 
            this.buttonSave.Enabled = false;
            this.buttonSave.Image = ((System.Drawing.Image)(resources.GetObject("buttonSave.Image")));
            this.buttonSave.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.buttonSave.Location = new System.Drawing.Point(29, 338);
            this.buttonSave.Name = "buttonSave";
            this.buttonSave.Size = new System.Drawing.Size(59, 26);
            this.buttonSave.TabIndex = 18;
            this.buttonSave.Text = "Save";
            this.buttonSave.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.buttonSave.UseVisualStyleBackColor = true;
            this.buttonSave.Click += new System.EventHandler(this.buttonSave_Click);
            // 
            // buttonCopyToClipboard
            // 
            this.buttonCopyToClipboard.ImageIndex = 0;
            this.buttonCopyToClipboard.ImageList = this.imageListButtons;
            this.buttonCopyToClipboard.Location = new System.Drawing.Point(346, 128);
            this.buttonCopyToClipboard.Name = "buttonCopyToClipboard";
            this.buttonCopyToClipboard.Size = new System.Drawing.Size(22, 20);
            this.buttonCopyToClipboard.TabIndex = 10;
            this.toolTipPasswordLength.SetToolTip(this.buttonCopyToClipboard, "Copy to clipboard");
            this.buttonCopyToClipboard.UseVisualStyleBackColor = true;
            this.buttonCopyToClipboard.Click += new System.EventHandler(this.buttonCopyToClipboard_Click);
            // 
            // imageListButtons
            // 
            this.imageListButtons.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListButtons.ImageStream")));
            this.imageListButtons.TransparentColor = System.Drawing.Color.Transparent;
            this.imageListButtons.Images.SetKeyName(0, "tango-copy.png");
            this.imageListButtons.Images.SetKeyName(1, "tango-cabinet.png");
            // 
            // labelCopyright
            // 
            this.labelCopyright.AutoSize = true;
            this.labelCopyright.Location = new System.Drawing.Point(12, 462);
            this.labelCopyright.Name = "labelCopyright";
            this.labelCopyright.Size = new System.Drawing.Size(156, 13);
            this.labelCopyright.TabIndex = 25;
            this.labelCopyright.Text = "(c) 2011-2012 Hugues Johnson";
            // 
            // linkLabelHJ
            // 
            this.linkLabelHJ.AutoSize = true;
            this.linkLabelHJ.LinkColor = System.Drawing.Color.Blue;
            this.linkLabelHJ.Location = new System.Drawing.Point(239, 475);
            this.linkLabelHJ.Name = "linkLabelHJ";
            this.linkLabelHJ.Size = new System.Drawing.Size(143, 13);
            this.linkLabelHJ.TabIndex = 27;
            this.linkLabelHJ.TabStop = true;
            this.linkLabelHJ.Text = "http://HuguesJohnson.com/";
            this.linkLabelHJ.VisitedLinkColor = System.Drawing.Color.Blue;
            this.linkLabelHJ.LinkClicked += new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.linkLabelHJ_LinkClicked);
            // 
            // labelGpl
            // 
            this.labelGpl.AutoSize = true;
            this.labelGpl.Location = new System.Drawing.Point(12, 475);
            this.labelGpl.Name = "labelGpl";
            this.labelGpl.Size = new System.Drawing.Size(168, 13);
            this.labelGpl.TabIndex = 26;
            this.labelGpl.Text = "";
            // 
            // comboBoxPasswordName
            // 
            this.comboBoxPasswordName.FormattingEnabled = true;
            this.comboBoxPasswordName.Location = new System.Drawing.Point(117, 72);
            this.comboBoxPasswordName.Name = "comboBoxPasswordName";
            this.comboBoxPasswordName.Size = new System.Drawing.Size(251, 21);
            this.comboBoxPasswordName.Sorted = true;
            this.comboBoxPasswordName.TabIndex = 5;
            this.comboBoxPasswordName.SelectedValueChanged += new System.EventHandler(this.comboBoxPasswordName_SelectedValueChanged);
            this.comboBoxPasswordName.TextChanged += new System.EventHandler(this.comboBoxPasswordName_TextChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(94, 351);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(229, 13);
            this.label1.TabIndex = 20;
            this.label1.Text = "Pass Phrase and Password are NEVER saved.";
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(94, 338);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(264, 13);
            this.label2.TabIndex = 19;
            this.label2.Text = "Save selected options for the current Password Name.";
            this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // checkBoxClearOnMinimize
            // 
            this.checkBoxClearOnMinimize.AutoSize = true;
            this.checkBoxClearOnMinimize.Location = new System.Drawing.Point(29, 385);
            this.checkBoxClearOnMinimize.Name = "checkBoxClearOnMinimize";
            this.checkBoxClearOnMinimize.Size = new System.Drawing.Size(204, 17);
            this.checkBoxClearOnMinimize.TabIndex = 21;
            this.checkBoxClearOnMinimize.Text = "Clear fields when window in minimized";
            this.checkBoxClearOnMinimize.UseVisualStyleBackColor = true;
            // 
            // checkBoxClearOnInactivity
            // 
            this.checkBoxClearOnInactivity.AutoSize = true;
            this.checkBoxClearOnInactivity.Location = new System.Drawing.Point(29, 408);
            this.checkBoxClearOnInactivity.Name = "checkBoxClearOnInactivity";
            this.checkBoxClearOnInactivity.Size = new System.Drawing.Size(101, 17);
            this.checkBoxClearOnInactivity.TabIndex = 22;
            this.checkBoxClearOnInactivity.Text = "Clear fields after";
            this.checkBoxClearOnInactivity.UseVisualStyleBackColor = true;
            this.checkBoxClearOnInactivity.CheckedChanged += new System.EventHandler(this.checkBoxClearOnInactivity_CheckedChanged);
            // 
            // textBoxSeconds
            // 
            this.textBoxSeconds.Location = new System.Drawing.Point(129, 407);
            this.textBoxSeconds.MaxLength = 4;
            this.textBoxSeconds.Name = "textBoxSeconds";
            this.textBoxSeconds.Size = new System.Drawing.Size(28, 20);
            this.textBoxSeconds.TabIndex = 23;
            this.textBoxSeconds.Text = "90";
            this.textBoxSeconds.Validating += new System.ComponentModel.CancelEventHandler(this.textBoxSeconds_Validating);
            // 
            // labelSecondsOfInactivity
            // 
            this.labelSecondsOfInactivity.AutoSize = true;
            this.labelSecondsOfInactivity.Location = new System.Drawing.Point(160, 409);
            this.labelSecondsOfInactivity.Name = "labelSecondsOfInactivity";
            this.labelSecondsOfInactivity.Size = new System.Drawing.Size(103, 13);
            this.labelSecondsOfInactivity.TabIndex = 24;
            this.labelSecondsOfInactivity.Text = "seconds of inactivity";
            this.labelSecondsOfInactivity.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // timer
            // 
            this.timer.Enabled = true;
            this.timer.Interval = 90000;
            this.timer.Tick += new System.EventHandler(this.timer_Tick);
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(388, 497);
            this.Controls.Add(this.labelSecondsOfInactivity);
            this.Controls.Add(this.textBoxSeconds);
            this.Controls.Add(this.checkBoxClearOnInactivity);
            this.Controls.Add(this.checkBoxClearOnMinimize);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.buttonSave);
            this.Controls.Add(this.comboBoxPasswordName);
            this.Controls.Add(this.labelGpl);
            this.Controls.Add(this.linkLabelHJ);
            this.Controls.Add(this.labelCopyright);
            this.Controls.Add(this.buttonCopyToClipboard);
            this.Controls.Add(this.groupBoxOptions);
            this.Controls.Add(this.textBoxPassword);
            this.Controls.Add(this.labelPassword);
            this.Controls.Add(this.labelPasswordNameHint);
            this.Controls.Add(this.labelPasswordName);
            this.Controls.Add(this.labelPassPhraseHint);
            this.Controls.Add(this.textBoxPassPhrase);
            this.Controls.Add(this.labelPassPhrase);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "MainForm";
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Text = "NARPassword for Windows v1.1";
            this.Deactivate += new System.EventHandler(this.MainForm_Deactivate);
            this.Resize += new System.EventHandler(this.MainForm_Resize);
            this.groupBoxOptions.ResumeLayout(false);
            this.groupBoxOptions.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.trackBarPasswordLength)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label labelPassPhrase;
        private System.Windows.Forms.TextBox textBoxPassPhrase;
        private System.Windows.Forms.Label labelPassPhraseHint;
        private System.Windows.Forms.Label labelPasswordName;
        private System.Windows.Forms.Label labelPasswordNameHint;
        private System.Windows.Forms.Label labelPassword;
        private System.Windows.Forms.TextBox textBoxPassword;
        private System.Windows.Forms.GroupBox groupBoxOptions;
        private System.Windows.Forms.CheckBox checkBoxUseSpecialCharacters;
        private System.Windows.Forms.CheckBox checkBoxUseNumbers;
        private System.Windows.Forms.CheckBox checkBoxUseUCase;
        private System.Windows.Forms.CheckBox checkBoxUseLCase;
        private System.Windows.Forms.Label labelPasswordLength;
        private System.Windows.Forms.TrackBar trackBarPasswordLength;
        private System.Windows.Forms.ToolTip toolTipPasswordLength;
        private System.Windows.Forms.ImageList imageListButtons;
        private System.Windows.Forms.Button buttonCopyToClipboard;
        private System.Windows.Forms.Label labelCopyright;
        private System.Windows.Forms.LinkLabel linkLabelHJ;
        private System.Windows.Forms.Label labelGpl;
        private System.Windows.Forms.ComboBox comboBoxPasswordName;
        private System.Windows.Forms.Button buttonSave;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.CheckBox checkBoxClearOnMinimize;
        private System.Windows.Forms.CheckBox checkBoxClearOnInactivity;
        private System.Windows.Forms.TextBox textBoxSeconds;
        private System.Windows.Forms.Label labelSecondsOfInactivity;
        private System.Windows.Forms.Timer timer;
    }
}

