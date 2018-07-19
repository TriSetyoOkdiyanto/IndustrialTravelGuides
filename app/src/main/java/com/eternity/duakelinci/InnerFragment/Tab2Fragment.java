package com.eternity.duakelinci.InnerFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.eternity.duakelinci.R;
import com.eternity.duakelinci.mail.Config;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Tab2Fragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
	private Button  insertbtn;
	private EditText editTextNama;
	private EditText editTextSekolah;
	private EditText editTextJml;
	private EditText editTextTgl;
	private EditText editTextJam;
	private EditText editTextHP;
	private EditText editTextEmail;
	private ImageView tanggal;
	private ImageView jam;
	private CheckBox mode24Hours;
	Context context;
	private FragmentActivity myContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.tab2fragment, container, false);
		insertbtn = (Button) v.findViewById(R.id.InsertBtn);
		editTextNama = (EditText) v.findViewById(R.id.editTextNama);
		editTextSekolah = (EditText) v.findViewById(R.id.editTextSekolah);
		editTextJml = (EditText) v.findViewById(R.id.editTextJumlah);
		editTextTgl = (EditText) v.findViewById(R.id.editTextTgl);
		editTextJam = (EditText) v.findViewById(R.id.editTextJam);
		editTextHP = (EditText) v.findViewById(R.id.editTextHP);
		editTextEmail = (EditText) v.findViewById(R.id.editTextEmail);
		tanggal = (ImageView) v.findViewById(R.id.tanggal);
		jam = (ImageView) v.findViewById(R.id.jam);
		mode24Hours = (CheckBox) v.findViewById(R.id.mode_24_hours);
		context = getActivity();
		editTextJam.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				validateEditText(s);
			}


		});

		editTextJam.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					validateEditText(((EditText) v).getText());
				}
			}
		});
		//Displaying TextInputLayout Error
		TextInputLayout textView3 = (TextInputLayout) v.findViewById(R.id
				.textView3);
		textView3.setErrorEnabled(true);
		textView3.setError("Maksimal 250 Peserta");

		TextInputLayout textView5 = (TextInputLayout) v.findViewById(R.id
				.textView5);
		textView5.setErrorEnabled(true);
		textView5.setError("08:00/10:00/13:00");

		//Displaying both TextInputLayout and EditText Errors


		tanggal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar now = Calendar.getInstance();
				DatePickerDialog dpd = DatePickerDialog.newInstance(
						Tab2Fragment.this,
						now.get(Calendar.YEAR),
						now.get(Calendar.MONTH),
						now.get(Calendar.DAY_OF_MONTH)
				);
				dpd.show(myContext.getFragmentManager(), "Datepickerdialog");
			}
		});

		jam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar now = Calendar.getInstance();
				TimePickerDialog tpd = TimePickerDialog.newInstance(
						Tab2Fragment.this,
						now.get(Calendar.HOUR_OF_DAY),
						now.get(Calendar.MINUTE),
						mode24Hours.isChecked()
				);
				tpd.show(myContext.getFragmentManager(), "Timepickerdialog");
			}
		});

		insertbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final String nama = editTextNama.getText().toString();
				final String sekolah = editTextSekolah.getText().toString();
				final String jml = editTextJml.getText().toString();
				final String jam = editTextJam.getText().toString();
				final String tgl = editTextTgl.getText().toString();
				final String hp = editTextHP.getText().toString();
				final String email = editTextEmail.getText().toString();

				if (!nama.equals("") && !sekolah.equals("") && !jml.equals("") && !tgl.equals("") && (jam.equals("08:00") || jam.equals("10:00") || jam.equals("13:00")) && !hp.equals("") && !email.equals("")) {
					// Do your stuff here
					ConnectivityManager cManager = (ConnectivityManager)
							context.getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo nInfo = cManager.getActiveNetworkInfo();
					if (nInfo != null && nInfo.isConnected()) {
						new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
								.setTitleText("Cek Kembali Data.")
								.setContentText("Data sebagai berikut\n\n" +
										"Nama: " + nama + "\n" +
										"Sekolah/Universitas: " + sekolah + "\n" +
										"Jumlah Peserta: " + jml + "\n" +
										"Tanggal: " + tgl + "\n" +
										"Jam: " + jam + "\n" +
										"Nomor HP: " + hp + "\n" +
										"Email: " + email)
								.setCancelText("Salah")
								.setConfirmText("Ya, Benar")
								.showCancelButton(true)
								.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

									@Override
									public void onClick(SweetAlertDialog sDialog) {
										// reuse previous dialog instance
										insertToDatabase(nama, sekolah, jml, tgl, jam, hp, email);
										sendEmail();
										sDialog.setTitleText("Data Tersimpan")
												.setContentText("Silahkan Tunggu Konfirmasi dari PT. Dua Kelinci")
												.setConfirmText("OK")
												.showCancelButton(false)
												.setConfirmClickListener(null)
												.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

										resetVariabel();
									}
								})
								.show();

					} else {
						Toast.makeText(context, "Tidak tersambung ke jaringan.", Toast.LENGTH_LONG).show();
					}
				} else if (nama.equals("")) {
					Toast.makeText(context, "Harap masukan nama", Toast.LENGTH_SHORT).show();
				} else if (sekolah.equals("")) {
					Toast.makeText(context, "Harap masukan sekolah", Toast.LENGTH_SHORT).show();
				} else if (jml.equals("")) {
					Toast.makeText(context, "Harap masukan jumlah peserta", Toast.LENGTH_SHORT).show();
				} else if (tgl.equals("")) {
					Toast.makeText(context, "Harap masukan tanggal", Toast.LENGTH_SHORT).show();
				} else if (jam.equals("")) {
					Toast.makeText(context, "Harap masukan jam", Toast.LENGTH_SHORT).show();
				} else if (hp.equals("")) {
					Toast.makeText(context, "Harap masukan nomor HP", Toast.LENGTH_SHORT).show();

				} else if (email.equals("")) {
					Toast.makeText(context, "Harap masukan email", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(context, "Harap masukan data dengan benar", Toast.LENGTH_SHORT).show();
					editTextJam.setError("Lihat Jadwal");
				}

			}
		});
		return v;
	}


	private void insertToDatabase(final String nama, String sekolah, String jml, String tgl, String jam, String hp, String email) {
		class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
			@Override
			protected String doInBackground(String... params) {
				String nama = params[0];
				String sekolah = params[1];
				String jml = params[2];
				String tgl = params[3];
				String jam = params[4];
				String hp = params[5];
				String email = params[6];


				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("nama", nama));
				nameValuePairs.add(new BasicNameValuePair("nama_sklh_pt", sekolah));
				nameValuePairs.add(new BasicNameValuePair("jml_peserta", jml));
				nameValuePairs.add(new BasicNameValuePair("tgl", tgl));
				nameValuePairs.add(new BasicNameValuePair("jam", jam));
				nameValuePairs.add(new BasicNameValuePair("no_hp", hp));
				nameValuePairs.add(new BasicNameValuePair("email", email));

				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(
							"http://duakelinci-wi.esy.es/insert-db.php");
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					HttpResponse response = httpClient.execute(httpPost);

					HttpEntity entity = response.getEntity();


				} catch (ClientProtocolException e) {

				} catch (IOException e) {

				}
				return "Data Tersimpan";
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);


				//Toast.makeText(context, result, Toast.LENGTH_LONG).show();
			}
		}
		SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
		sendPostReqAsyncTask.execute(nama, sekolah, jml, tgl, jam, hp, email);
	}

	private void sendEmail() {

		final String nama = editTextNama.getText().toString();
		final String sekolah = editTextSekolah.getText().toString();
		final String jml = editTextJml.getText().toString();
		final String jam = editTextJam.getText().toString();
		final String tgl = editTextTgl.getText().toString();
		final String hp = editTextHP.getText().toString();
		final String email = editTextEmail.getText().toString();

		//Creating SendMail object
		SendMail sm = new SendMail(myContext, "dkwisataindustri@gmail.com", "Booking Wisata Industri", "Nama	: " + nama + "\n Sekolah/Universitas : " + sekolah +
				"\n Jumlah Peserta : " + jml + "\n Jam : " + jam + "\n Tanggal : " + tgl + "\n Nomor HP : " + hp + "\n Email : " + email);

		//Executing sendmail to send email
		sm.execute();
	}

	public class SendMail extends AsyncTask<Void,Void,Void> {

		//Declaring Variables
		private Context context;
		private Session session;

		//Information to send email
		private String email;
		private String subject;
		private String message;

		//Progressdialog to show while sending email
		private ProgressDialog progressDialog;

		//Class Constructor
		public SendMail(Context context, String email, String subject, String message){
			//Initializing variables
			this.context = context;
			this.email = email;
			this.subject = subject;
			this.message = message;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//Showing progress dialog while sending email
			progressDialog = ProgressDialog.show(context,"Mengirim","Silahkan tunggu...",false,false);
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			//Dismissing the progress dialog
			progressDialog.dismiss();

			//Showing a success message
			//Toast.makeText(context,"Message Sent",Toast.LENGTH_LONG).show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			//Creating properties
			Properties props = new Properties();

			//Configuring properties for gmail
			//If you are not using gmail you may need to change the values
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			//Creating a new session
			session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						//Authenticating the password
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
						}
					});

			try {
				//Creating MimeMessage object
				MimeMessage mm = new MimeMessage(session);

				//Setting sender address
				mm.setFrom(new InternetAddress(Config.EMAIL));
				//Adding receiver
				mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				//Adding subject
				mm.setSubject(subject);
				//Adding message
				mm.setText(message);

				//Sending email
				Transport.send(mm);

			} catch (MessagingException e) {
				e.printStackTrace();
			}
			return null;
		}
	}


	protected void resetVariabel() {
		editTextNama.setText(null);
		editTextSekolah.setText(null);
		editTextJml.setText(null);
		editTextJam.setText(null);
		editTextTgl.setText(null);
		editTextHP.setText(null);
		editTextEmail.setText(null);
		editTextJam.setError(null);
	}

	@Override
	public void onAttach(Activity activity) {
		myContext=(FragmentActivity) activity;
		super.onAttach(activity);
	}

	@Override
	public void onResume() {
		super.onResume();

		DatePickerDialog dpd = (DatePickerDialog) myContext.getFragmentManager().findFragmentByTag("Datepickerdialog");
		TimePickerDialog tpd = (TimePickerDialog) myContext.getFragmentManager().findFragmentByTag("TimepickerDialog");

		if(tpd != null) tpd.setOnTimeSetListener(this);
		if(dpd != null) dpd.setOnDateSetListener(this);
	}

	@Override
	public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
		String date = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
		editTextTgl.setText(date);
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
		String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
		String minuteString = minute < 10 ? "0" + minute : "" + minute;
		String secondString = second < 10 ? "0" + second : "" + second;
		String time = hourString + ":" + minuteString;
		editTextJam.setText(time);
	}
	private void validateEditText(Editable s) {
		String jam = editTextJam.getText().toString();
		if (jam.equals("08:00")) {
			editTextJam.setError(null);
		}else if (jam.equals("10:00")) {
			editTextJam.setError(null);
		}else if (jam.equals("13:00")) {
			editTextJam.setError(null);
		}
	}
}
