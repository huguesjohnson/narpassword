/*
NARPassword for Windows - Application to generate a non-random password
Copyright (C) 2011 Hugues Johnson

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; version 2.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
the GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software 
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
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
