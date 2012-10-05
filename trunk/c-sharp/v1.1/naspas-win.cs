/*
NARPassword for Windows - Application to generate a non-random password
Copyright (C) 2011-2012 Hugues Johnson

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
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Json;
using System.Text;
using System.Windows.Forms;

namespace narpaswin
{
    public partial class MainForm:Form
    {
        public MainForm()
        {
            InitializeComponent();
            this.loadSavedPasswordOptions();
        }

        private void loadSavedPasswordOptions() 
        {
            try
            {
                if(File.Exists(Application.UserAppDataPath+"\\options.json")) 
                {
                    List<PasswordOptions> options=null;
                    using(FileStream reader=File.OpenRead(Application.UserAppDataPath+"\\options.json"))
                    {
                        DataContractJsonSerializer jsonSerializer=new DataContractJsonSerializer(typeof(List<PasswordOptions>));
                        options=(List<PasswordOptions>)jsonSerializer.ReadObject(reader);
                    }
                    if(options!=null)
                    {
                        this.comboBoxPasswordName.Items.AddRange(options.ToArray());
                    }
                }
            }
            catch(Exception x)
            {
                String fdf=x.Message.ToString();
            }
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
            String currentPassword=textBoxPassword.Text;
            bool useLCase=checkBoxUseLCase.Checked;
            bool useUCase=checkBoxUseUCase.Checked;
            bool useNumbers=checkBoxUseNumbers.Checked;
            bool useSpecialCharacters=checkBoxUseSpecialCharacters.Checked;
            textBoxPassword.Text=Narpas.generatePassword
                (
                    textBoxPassPhrase.Text,
                    comboBoxPasswordName.Text,
                    checkBoxUseLCase.Checked,
                    checkBoxUseUCase.Checked,
                    checkBoxUseNumbers.Checked,
                    checkBoxUseSpecialCharacters.Checked,
                    trackBarPasswordLength.Value
                    );
            textBoxPassword.SelectAll();
            //if the password changed then enable the save button, if the password is blank disable it
            if(!buttonSave.Enabled)
            {
                if(!currentPassword.Equals(textBoxPassword.Text))
                {
                    buttonSave.Enabled=true;
                }
            }
            else if(textBoxPassword.Text.Length<1)
            {
                buttonSave.Enabled=false;
            }
            //reset the timer
            if(this.checkBoxClearOnInactivity.Checked) 
            {
                this.timer.Stop();
                this.timer.Start();
            }
        }

        private void buttonCopyToClipboard_Click(object sender,EventArgs e)
        {
            if((textBoxPassword.Text!=null)&&(textBoxPassword.Text.Length>0))
            {
                Clipboard.SetText(textBoxPassword.Text,TextDataFormat.Text);
            }
        }

        private void linkLabelHJ_LinkClicked(object sender,LinkLabelLinkClickedEventArgs e)
        {
            Process.Start(new ProcessStartInfo("http://huguesjohnson.com/"));
        }

        private void comboBoxPasswordName_TextChanged(object sender,EventArgs e)
        {
            generatePassword();
        }

        private void MainForm_Resize(object sender,EventArgs e)
        {
            if(this.checkBoxClearOnMinimize.Checked) 
            {
                if(this.WindowState.Equals(FormWindowState.Minimized)) 
                {
                    this.textBoxPassword.Text="";
                    this.textBoxPassPhrase.Text="";
                    this.comboBoxPasswordName.Text="";
                    this.buttonSave.Enabled=false;
                }
            }
        }

        private void timer_Tick(object sender,EventArgs e)
        {
            if(this.checkBoxClearOnInactivity.Checked)
            {
                this.textBoxPassword.Text="";
                this.textBoxPassPhrase.Text="";
                this.comboBoxPasswordName.Text="";
                this.buttonSave.Enabled=false;
            }
        }

        private void checkBoxClearOnInactivity_CheckedChanged(object sender,EventArgs e)
        {
            if(this.checkBoxClearOnInactivity.Checked)
            {
                this.timer.Start();
            }
            else 
            {
                this.timer.Stop();
            }
        }

        private void buttonSave_Click(object sender,EventArgs e)
        {
            PasswordOptions options=new PasswordOptions();
            options.Name=this.comboBoxPasswordName.Text;
            options.UseLowerCase=this.checkBoxUseLCase.Checked;
            options.UseNumbers=this.checkBoxUseNumbers.Checked;
            options.UseSpecialCharacters=this.checkBoxUseSpecialCharacters.Checked;
            options.UseUpperCase=this.checkBoxUseUCase.Checked;
            options.Length=this.trackBarPasswordLength.Value;
            int index=this.comboBoxPasswordName.Items.IndexOf(options);//this.passwords.IndexOf(options);
            if(index>-1)
            {
                this.comboBoxPasswordName.Items[index]=options;
            }
            else 
            {
                this.comboBoxPasswordName.Items.Add(options);
            }
            buttonSave.Enabled=false;
        }

        private void comboBoxPasswordName_SelectedValueChanged(object sender,EventArgs e)
        {
            PasswordOptions options=(PasswordOptions)this.comboBoxPasswordName.SelectedItem;
            this.checkBoxUseLCase.Checked=options.UseLowerCase;
            this.checkBoxUseNumbers.Checked=options.UseNumbers;
            this.checkBoxUseSpecialCharacters.Checked=options.UseSpecialCharacters;
            this.checkBoxUseUCase.Checked=options.UseUpperCase;
            this.trackBarPasswordLength.Value=options.Length;
        }

        private void textBoxSeconds_Validating(object sender,CancelEventArgs e)
        {
            try
            {
                int seconds=Int32.Parse(textBoxSeconds.Text);
                this.timer.Interval=seconds*1000;
                if(this.checkBoxClearOnInactivity.Checked) 
                {
                    this.timer.Stop();
                    this.timer.Start();
                }
            }
            catch
            {
                e.Cancel=true;
                textBoxSeconds.Select(0,textBoxSeconds.Text.Length);
            }
        }

        private void MainForm_Deactivate(object sender,EventArgs e)
        {
            try
            {
                int size=comboBoxPasswordName.Items.Count;
                List<PasswordOptions> options=new List<PasswordOptions>(size);
                for(int index=0;index<size;index++)
                {
                    options.Add((PasswordOptions)comboBoxPasswordName.Items[index]);
                }
                DataContractJsonSerializer jsonSerializer=new DataContractJsonSerializer(typeof(List<PasswordOptions>));
                using(FileStream writer=File.OpenWrite(Application.UserAppDataPath+"\\options.json"))
                {
                    jsonSerializer.WriteObject(writer,options);
                }
            }
            catch { }
        }
    }
}
