/*
NARPassword for Windows - Application to generate a non-random password
Copyright (C) 2011-2018 Hugues Johnson

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace narpaswin
{
    public partial class MainForm:Form
    {
        public MainForm()
        {
            InitializeComponent();
        }

        private void trackBarPasswordLength_Scroll(object sender,EventArgs e)
        {
            toolTipPasswordLength.SetToolTip(trackBarPasswordLength,(trackBarPasswordLength.Value*8).ToString());
        }

        private void textBoxPassPhrase_TextChanged(object sender,EventArgs e)
        {
            generatePassword();
        }

        private void textBoxPasswordName_TextChanged(object sender,EventArgs e)
        {
            generatePassword();
        }

        private void checkBoxUseLCase_CheckedChanged(object sender,EventArgs e)
        {
            generatePassword();
        }

        private void checkBoxUseUCase_CheckedChanged(object sender,EventArgs e)
        {
            generatePassword();
        }

        private void checkBoxUseNumbers_CheckedChanged(object sender,EventArgs e)
        {
            generatePassword();
        }

        private void checkBoxUseSpecialCharacters_CheckedChanged(object sender,EventArgs e)
        {
            generatePassword();
        }

        private void trackBarPasswordLength_ValueChanged(object sender,EventArgs e)
        {
            generatePassword();
        }

        private void generatePassword() 
        {
            bool useLCase=checkBoxUseLCase.Checked;
            bool useUCase=checkBoxUseUCase.Checked;
            bool useNumbers=checkBoxUseNumbers.Checked;
            bool useSpecialCharacters=checkBoxUseSpecialCharacters.Checked;
            textBoxPassword.Text=Narpas.generatePassword
                (
                    textBoxPassPhrase.Text,
                    textBoxPasswordName.Text,
                    checkBoxUseLCase.Checked,
                    checkBoxUseUCase.Checked,
                    checkBoxUseNumbers.Checked,
                    checkBoxUseSpecialCharacters.Checked,
                    trackBarPasswordLength.Value
                    );
            textBoxPassword.SelectAll();
        }

        private void checkBoxMaskPassPhrase_CheckStateChanged(object sender,EventArgs e)
        {
            if(checkBoxMaskPassPhrase.Checked)
            {
                textBoxPassPhrase.PasswordChar='*';
            }
            else 
            {
                textBoxPassPhrase.PasswordChar='\0';
            }
        }

        private void checkBoxMaskPasswordName_CheckStateChanged(object sender,EventArgs e)
        {
            if(checkBoxMaskPasswordName.Checked)
            {
                textBoxPasswordName.PasswordChar='*';
            }
            else
            {
                textBoxPasswordName.PasswordChar='\0';
            }
        }

        private void checkBoxMaskPassword_CheckStateChanged(object sender,EventArgs e)
        {
            if(checkBoxMaskPassword.Checked)
            {
                textBoxPassword.PasswordChar='*';
            }
            else
            {
                textBoxPassword.PasswordChar='\0';
            }
        }

        private void buttonCopyToClipboard_Click(object sender,EventArgs e)
        {
            Clipboard.SetText(textBoxPassword.Text,TextDataFormat.Text);
        }

        private void linkLabelHJ_LinkClicked(object sender,LinkLabelLinkClickedEventArgs e)
        {
            Process.Start(new ProcessStartInfo("http://huguesjohnson.com/"));
        }
    }
}
