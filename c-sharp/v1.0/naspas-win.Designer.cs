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
            this.components=new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources=new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            this.labelPassPhrase=new System.Windows.Forms.Label();
            this.textBoxPassPhrase=new System.Windows.Forms.TextBox();
            this.labelPassPhraseHint=new System.Windows.Forms.Label();
            this.labelPasswordName=new System.Windows.Forms.Label();
            this.textBoxPasswordName=new System.Windows.Forms.TextBox();
            this.labelPasswordNameHint=new System.Windows.Forms.Label();
            this.labelPassword=new System.Windows.Forms.Label();
            this.textBoxPassword=new System.Windows.Forms.TextBox();
            this.groupBoxOptions=new System.Windows.Forms.GroupBox();
            this.labelPasswordLength=new System.Windows.Forms.Label();
            this.trackBarPasswordLength=new System.Windows.Forms.TrackBar();
            this.checkBoxUseSpecialCharacters=new System.Windows.Forms.CheckBox();
            this.checkBoxUseNumbers=new System.Windows.Forms.CheckBox();
            this.checkBoxUseUCase=new System.Windows.Forms.CheckBox();
            this.checkBoxUseLCase=new System.Windows.Forms.CheckBox();
            this.toolTipPasswordLength=new System.Windows.Forms.ToolTip(this.components);
            this.buttonCopyToClipboard=new System.Windows.Forms.Button();
            this.imageListButtons=new System.Windows.Forms.ImageList(this.components);
            this.groupBoxUIOptions=new System.Windows.Forms.GroupBox();
            this.checkBoxMaskPassword=new System.Windows.Forms.CheckBox();
            this.checkBoxMaskPasswordName=new System.Windows.Forms.CheckBox();
            this.checkBoxMaskPassPhrase=new System.Windows.Forms.CheckBox();
            this.labelCopyright=new System.Windows.Forms.Label();
            this.linkLabelHJ=new System.Windows.Forms.LinkLabel();
            this.label1=new System.Windows.Forms.Label();
            this.groupBoxOptions.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.trackBarPasswordLength)).BeginInit();
            this.groupBoxUIOptions.SuspendLayout();
            this.SuspendLayout();
            // 
            // labelPassPhrase
            // 
            this.labelPassPhrase.AutoSize=true;
            this.labelPassPhrase.Location=new System.Drawing.Point(42,24);
            this.labelPassPhrase.Name="labelPassPhrase";
            this.labelPassPhrase.Size=new System.Drawing.Size(69,13);
            this.labelPassPhrase.TabIndex=0;
            this.labelPassPhrase.Text="Pass Phrase:";
            // 
            // textBoxPassPhrase
            // 
            this.textBoxPassPhrase.Location=new System.Drawing.Point(117,21);
            this.textBoxPassPhrase.Name="textBoxPassPhrase";
            this.textBoxPassPhrase.Size=new System.Drawing.Size(251,20);
            this.textBoxPassPhrase.TabIndex=1;
            this.textBoxPassPhrase.TextChanged+=new System.EventHandler(this.textBoxPassPhrase_TextChanged);
            // 
            // labelPassPhraseHint
            // 
            this.labelPassPhraseHint.AutoSize=true;
            this.labelPassPhraseHint.ForeColor=System.Drawing.SystemColors.InactiveCaptionText;
            this.labelPassPhraseHint.Location=new System.Drawing.Point(123,44);
            this.labelPassPhraseHint.Name="labelPassPhraseHint";
            this.labelPassPhraseHint.Size=new System.Drawing.Size(248,13);
            this.labelPassPhraseHint.TabIndex=2;
            this.labelPassPhraseHint.Text="i.e. \"I like pie\" or \"Please no more California songs\"";
            // 
            // labelPasswordName
            // 
            this.labelPasswordName.AutoSize=true;
            this.labelPasswordName.Location=new System.Drawing.Point(26,75);
            this.labelPasswordName.Name="labelPasswordName";
            this.labelPasswordName.Size=new System.Drawing.Size(87,13);
            this.labelPasswordName.TabIndex=3;
            this.labelPasswordName.Text="Password Name:";
            // 
            // textBoxPasswordName
            // 
            this.textBoxPasswordName.Location=new System.Drawing.Point(117,72);
            this.textBoxPasswordName.Name="textBoxPasswordName";
            this.textBoxPasswordName.Size=new System.Drawing.Size(251,20);
            this.textBoxPasswordName.TabIndex=2;
            this.textBoxPasswordName.TextChanged+=new System.EventHandler(this.textBoxPasswordName_TextChanged);
            // 
            // labelPasswordNameHint
            // 
            this.labelPasswordNameHint.AutoSize=true;
            this.labelPasswordNameHint.ForeColor=System.Drawing.SystemColors.InactiveCaptionText;
            this.labelPasswordNameHint.Location=new System.Drawing.Point(123,95);
            this.labelPasswordNameHint.Name="labelPasswordNameHint";
            this.labelPasswordNameHint.Size=new System.Drawing.Size(211,13);
            this.labelPasswordNameHint.TabIndex=5;
            this.labelPasswordNameHint.Text="i.e. \"Facebook\" or \"yourname@gmail.com\"";
            // 
            // labelPassword
            // 
            this.labelPassword.AutoSize=true;
            this.labelPassword.Location=new System.Drawing.Point(55,131);
            this.labelPassword.Name="labelPassword";
            this.labelPassword.Size=new System.Drawing.Size(56,13);
            this.labelPassword.TabIndex=6;
            this.labelPassword.Text="Password:";
            // 
            // textBoxPassword
            // 
            this.textBoxPassword.HideSelection=false;
            this.textBoxPassword.Location=new System.Drawing.Point(117,128);
            this.textBoxPassword.Name="textBoxPassword";
            this.textBoxPassword.ReadOnly=true;
            this.textBoxPassword.Size=new System.Drawing.Size(230,20);
            this.textBoxPassword.TabIndex=4;
            // 
            // groupBoxOptions
            // 
            this.groupBoxOptions.Controls.Add(this.labelPasswordLength);
            this.groupBoxOptions.Controls.Add(this.trackBarPasswordLength);
            this.groupBoxOptions.Controls.Add(this.checkBoxUseSpecialCharacters);
            this.groupBoxOptions.Controls.Add(this.checkBoxUseNumbers);
            this.groupBoxOptions.Controls.Add(this.checkBoxUseUCase);
            this.groupBoxOptions.Controls.Add(this.checkBoxUseLCase);
            this.groupBoxOptions.Location=new System.Drawing.Point(29,166);
            this.groupBoxOptions.Name="groupBoxOptions";
            this.groupBoxOptions.Size=new System.Drawing.Size(339,162);
            this.groupBoxOptions.TabIndex=8;
            this.groupBoxOptions.TabStop=false;
            this.groupBoxOptions.Text="Password Options";
            // 
            // labelPasswordLength
            // 
            this.labelPasswordLength.AutoSize=true;
            this.labelPasswordLength.Location=new System.Drawing.Point(13,115);
            this.labelPasswordLength.Name="labelPasswordLength";
            this.labelPasswordLength.Size=new System.Drawing.Size(92,13);
            this.labelPasswordLength.TabIndex=5;
            this.labelPasswordLength.Text="Password Length:";
            // 
            // trackBarPasswordLength
            // 
            this.trackBarPasswordLength.LargeChange=16;
            this.trackBarPasswordLength.Location=new System.Drawing.Point(111,115);
            this.trackBarPasswordLength.Maximum=16;
            this.trackBarPasswordLength.Minimum=1;
            this.trackBarPasswordLength.Name="trackBarPasswordLength";
            this.trackBarPasswordLength.Size=new System.Drawing.Size(222,45);
            this.trackBarPasswordLength.SmallChange=8;
            this.trackBarPasswordLength.TabIndex=10;
            this.trackBarPasswordLength.Value=4;
            this.trackBarPasswordLength.Scroll+=new System.EventHandler(this.trackBarPasswordLength_Scroll);
            this.trackBarPasswordLength.ValueChanged+=new System.EventHandler(this.trackBarPasswordLength_ValueChanged);
            // 
            // checkBoxUseSpecialCharacters
            // 
            this.checkBoxUseSpecialCharacters.AutoSize=true;
            this.checkBoxUseSpecialCharacters.Checked=true;
            this.checkBoxUseSpecialCharacters.CheckState=System.Windows.Forms.CheckState.Checked;
            this.checkBoxUseSpecialCharacters.Location=new System.Drawing.Point(16,92);
            this.checkBoxUseSpecialCharacters.Name="checkBoxUseSpecialCharacters";
            this.checkBoxUseSpecialCharacters.Size=new System.Drawing.Size(221,17);
            this.checkBoxUseSpecialCharacters.TabIndex=9;
            this.checkBoxUseSpecialCharacters.Text="Use special characters (!@#$%^&*-=+:;?,.)";
            this.checkBoxUseSpecialCharacters.UseVisualStyleBackColor=true;
            this.checkBoxUseSpecialCharacters.CheckedChanged+=new System.EventHandler(this.checkBoxUseSpecialCharacters_CheckedChanged);
            // 
            // checkBoxUseNumbers
            // 
            this.checkBoxUseNumbers.AutoSize=true;
            this.checkBoxUseNumbers.Checked=true;
            this.checkBoxUseNumbers.CheckState=System.Windows.Forms.CheckState.Checked;
            this.checkBoxUseNumbers.Location=new System.Drawing.Point(16,69);
            this.checkBoxUseNumbers.Name="checkBoxUseNumbers";
            this.checkBoxUseNumbers.Size=new System.Drawing.Size(112,17);
            this.checkBoxUseNumbers.TabIndex=8;
            this.checkBoxUseNumbers.Text="Use numbers (0-9)";
            this.checkBoxUseNumbers.UseVisualStyleBackColor=true;
            this.checkBoxUseNumbers.CheckedChanged+=new System.EventHandler(this.checkBoxUseNumbers_CheckedChanged);
            // 
            // checkBoxUseUCase
            // 
            this.checkBoxUseUCase.AutoSize=true;
            this.checkBoxUseUCase.Checked=true;
            this.checkBoxUseUCase.CheckState=System.Windows.Forms.CheckState.Checked;
            this.checkBoxUseUCase.Location=new System.Drawing.Point(16,46);
            this.checkBoxUseUCase.Name="checkBoxUseUCase";
            this.checkBoxUseUCase.Size=new System.Drawing.Size(180,17);
            this.checkBoxUseUCase.TabIndex=7;
            this.checkBoxUseUCase.Text="Use upper case characters (A-Z)";
            this.checkBoxUseUCase.UseVisualStyleBackColor=true;
            this.checkBoxUseUCase.CheckedChanged+=new System.EventHandler(this.checkBoxUseUCase_CheckedChanged);
            // 
            // checkBoxUseLCase
            // 
            this.checkBoxUseLCase.AutoSize=true;
            this.checkBoxUseLCase.Checked=true;
            this.checkBoxUseLCase.CheckState=System.Windows.Forms.CheckState.Checked;
            this.checkBoxUseLCase.Location=new System.Drawing.Point(16,23);
            this.checkBoxUseLCase.Name="checkBoxUseLCase";
            this.checkBoxUseLCase.Size=new System.Drawing.Size(175,17);
            this.checkBoxUseLCase.TabIndex=6;
            this.checkBoxUseLCase.Text="Use lower case characters (a-z)";
            this.checkBoxUseLCase.UseVisualStyleBackColor=true;
            this.checkBoxUseLCase.CheckedChanged+=new System.EventHandler(this.checkBoxUseLCase_CheckedChanged);
            // 
            // buttonCopyToClipboard
            // 
            this.buttonCopyToClipboard.ImageIndex=0;
            this.buttonCopyToClipboard.ImageList=this.imageListButtons;
            this.buttonCopyToClipboard.Location=new System.Drawing.Point(346,128);
            this.buttonCopyToClipboard.Name="buttonCopyToClipboard";
            this.buttonCopyToClipboard.Size=new System.Drawing.Size(22,20);
            this.buttonCopyToClipboard.TabIndex=5;
            this.toolTipPasswordLength.SetToolTip(this.buttonCopyToClipboard,"Copy to clipboard");
            this.buttonCopyToClipboard.UseVisualStyleBackColor=true;
            this.buttonCopyToClipboard.Click+=new System.EventHandler(this.buttonCopyToClipboard_Click);
            // 
            // imageListButtons
            // 
            this.imageListButtons.ImageStream=((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListButtons.ImageStream")));
            this.imageListButtons.TransparentColor=System.Drawing.Color.Transparent;
            this.imageListButtons.Images.SetKeyName(0,"tango-copy.png");
            // 
            // groupBoxUIOptions
            // 
            this.groupBoxUIOptions.Controls.Add(this.checkBoxMaskPassword);
            this.groupBoxUIOptions.Controls.Add(this.checkBoxMaskPasswordName);
            this.groupBoxUIOptions.Controls.Add(this.checkBoxMaskPassPhrase);
            this.groupBoxUIOptions.Location=new System.Drawing.Point(29,334);
            this.groupBoxUIOptions.Name="groupBoxUIOptions";
            this.groupBoxUIOptions.Size=new System.Drawing.Size(338,105);
            this.groupBoxUIOptions.TabIndex=12;
            this.groupBoxUIOptions.TabStop=false;
            this.groupBoxUIOptions.Text="UI Options";
            // 
            // checkBoxMaskPassword
            // 
            this.checkBoxMaskPassword.AutoSize=true;
            this.checkBoxMaskPassword.Location=new System.Drawing.Point(16,74);
            this.checkBoxMaskPassword.Name="checkBoxMaskPassword";
            this.checkBoxMaskPassword.Size=new System.Drawing.Size(101,17);
            this.checkBoxMaskPassword.TabIndex=13;
            this.checkBoxMaskPassword.Text="Mask Password";
            this.checkBoxMaskPassword.UseVisualStyleBackColor=true;
            this.checkBoxMaskPassword.CheckStateChanged+=new System.EventHandler(this.checkBoxMaskPassword_CheckStateChanged);
            // 
            // checkBoxMaskPasswordName
            // 
            this.checkBoxMaskPasswordName.AutoSize=true;
            this.checkBoxMaskPasswordName.Location=new System.Drawing.Point(16,51);
            this.checkBoxMaskPasswordName.Name="checkBoxMaskPasswordName";
            this.checkBoxMaskPasswordName.Size=new System.Drawing.Size(132,17);
            this.checkBoxMaskPasswordName.TabIndex=12;
            this.checkBoxMaskPasswordName.Text="Mask Password Name";
            this.checkBoxMaskPasswordName.UseVisualStyleBackColor=true;
            this.checkBoxMaskPasswordName.CheckStateChanged+=new System.EventHandler(this.checkBoxMaskPasswordName_CheckStateChanged);
            // 
            // checkBoxMaskPassPhrase
            // 
            this.checkBoxMaskPassPhrase.AutoSize=true;
            this.checkBoxMaskPassPhrase.Location=new System.Drawing.Point(16,28);
            this.checkBoxMaskPassPhrase.Name="checkBoxMaskPassPhrase";
            this.checkBoxMaskPassPhrase.Size=new System.Drawing.Size(114,17);
            this.checkBoxMaskPassPhrase.TabIndex=11;
            this.checkBoxMaskPassPhrase.Text="Mask Pass Phrase";
            this.checkBoxMaskPassPhrase.UseVisualStyleBackColor=true;
            this.checkBoxMaskPassPhrase.CheckStateChanged+=new System.EventHandler(this.checkBoxMaskPassPhrase_CheckStateChanged);
            // 
            // labelCopyright
            // 
            this.labelCopyright.AutoSize=true;
            this.labelCopyright.Location=new System.Drawing.Point(12,454);
            this.labelCopyright.Name="labelCopyright";
            this.labelCopyright.Size=new System.Drawing.Size(129,13);
            this.labelCopyright.TabIndex=13;
            this.labelCopyright.Text="(c) 2011 Hugues Johnson";
            // 
            // linkLabelHJ
            // 
            this.linkLabelHJ.AutoSize=true;
            this.linkLabelHJ.LinkColor=System.Drawing.Color.Blue;
            this.linkLabelHJ.Location=new System.Drawing.Point(239,467);
            this.linkLabelHJ.Name="linkLabelHJ";
            this.linkLabelHJ.Size=new System.Drawing.Size(143,13);
            this.linkLabelHJ.TabIndex=14;
            this.linkLabelHJ.TabStop=true;
            this.linkLabelHJ.Text="http://HuguesJohnson.com/";
            this.linkLabelHJ.VisitedLinkColor=System.Drawing.Color.Blue;
            this.linkLabelHJ.LinkClicked+=new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.linkLabelHJ_LinkClicked);
            // 
            // label1
            // 
            this.label1.AutoSize=true;
            this.label1.Location=new System.Drawing.Point(12,467);
            this.label1.Name="label1";
            this.label1.Size=new System.Drawing.Size(168,13);
            this.label1.TabIndex=15;
            this.label1.Text="MIT License";
            // 
            // MainForm
            // 
            this.AutoScaleDimensions=new System.Drawing.SizeF(6F,13F);
            this.AutoScaleMode=System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize=new System.Drawing.Size(394,489);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.linkLabelHJ);
            this.Controls.Add(this.labelCopyright);
            this.Controls.Add(this.groupBoxUIOptions);
            this.Controls.Add(this.buttonCopyToClipboard);
            this.Controls.Add(this.groupBoxOptions);
            this.Controls.Add(this.textBoxPassword);
            this.Controls.Add(this.labelPassword);
            this.Controls.Add(this.labelPasswordNameHint);
            this.Controls.Add(this.textBoxPasswordName);
            this.Controls.Add(this.labelPasswordName);
            this.Controls.Add(this.labelPassPhraseHint);
            this.Controls.Add(this.textBoxPassPhrase);
            this.Controls.Add(this.labelPassPhrase);
            this.FormBorderStyle=System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Icon=((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox=false;
            this.Name="MainForm";
            this.SizeGripStyle=System.Windows.Forms.SizeGripStyle.Hide;
            this.Text="NARPassword for Windows v1";
            this.groupBoxOptions.ResumeLayout(false);
            this.groupBoxOptions.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.trackBarPasswordLength)).EndInit();
            this.groupBoxUIOptions.ResumeLayout(false);
            this.groupBoxUIOptions.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label labelPassPhrase;
        private System.Windows.Forms.TextBox textBoxPassPhrase;
        private System.Windows.Forms.Label labelPassPhraseHint;
        private System.Windows.Forms.Label labelPasswordName;
        private System.Windows.Forms.TextBox textBoxPasswordName;
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
        private System.Windows.Forms.GroupBox groupBoxUIOptions;
        private System.Windows.Forms.CheckBox checkBoxMaskPassword;
        private System.Windows.Forms.CheckBox checkBoxMaskPasswordName;
        private System.Windows.Forms.CheckBox checkBoxMaskPassPhrase;
        private System.Windows.Forms.Label labelCopyright;
        private System.Windows.Forms.LinkLabel linkLabelHJ;
        private System.Windows.Forms.Label label1;
    }
}

