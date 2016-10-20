package me.ranol.capjit;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import me.ranol.capjit.components.FolderFilter;
import me.ranol.capjit.components.RgbColorPicker;
import me.ranol.capjit.components.WrappedFileChooser;
import me.ranol.capjit.components.WrappedTextField;
import me.ranol.capjit.utils.Result;

public class OptionFrame extends JFrame {
	private static final long serialVersionUID = -438183573052120298L;
	private static JCheckBox save = new JCheckBox("캡쳐 후 저장하기");
	private static JCheckBox img = new JCheckBox("이미지 뷰어 띄우기");
	private static WrappedTextField dir = new WrappedTextField();
	private static WrappedTextField fn = new WrappedTextField();
	JTextArea desc = new JTextArea();
	JButton search = new JButton("찾아보기..");
	private static final Font DEFAULT_FONT = new Font("맑은 고딕", Font.BOLD, 15);
	SimpleOptionFile sof = new SimpleOptionFile(new File("."), "capjit.sof");
	private static RgbColorPicker color = new RgbColorPicker(Color.RED);
	private static OptionFrame instance;
	private static KeyData[] kd = new KeyData[3];

	{
		for (int i = 0; i < kd.length; i++) {
			kd[i] = new KeyData();
		}
	}

	public static boolean construct() {
		boolean c = instance != null;
		getInstance();
		return c;
	}

	public static OptionFrame getInstance() {
		if (instance == null)
			synchronized (OptionFrame.class) {
				instance = new OptionFrame();
			}
		return instance;
	}

	public void updateDescription() {
		desc.setText("SimpleDateFormat에 맞게\n" + "파일의 이름을 지정할 수 있습니다.\n"
				+ "y - 연도입니다. \n" + "M - 달입니다.\n" + "d - 날짜입니다.\n"
				+ "hh - 시간입니다.\n" + "m - 분입니다.\n" + "s - 초입니다.\n" + "예시: "
				+ format(fn.getText()));
	}

	public static String getDir() {
		return dir.getText().isEmpty() ? "." : dir.getText();
	}

	public static String formatNow() {
		return format(fn.getText());
	}

	public static String format(String s) {
		String data = "";
		int b = 0;
		int index = 0;
		int end = -404;
		while ((index = s.indexOf("{", index)) != -1) {
			String inside = s.substring(b < 0 ? 0 : b, index);
			b = index;
			end = inside.indexOf("}");
			if (end == -1) {
				index++;
				data += inside;
			} else {
				String match = inside.substring(0, end);
				data += replace(match) + inside.substring(end + 1);
			}
		}
		String last = s.substring(b);
		end = last.indexOf("}");
		if (end == -1) {
			index++;
			data += last;
		} else {
			String match = last.substring(0, end);
			data += replace(match) + last.substring(end + 1);
		}
		return data;
	}

	private static String replace(String s) {
		String result = s.replaceAll("[^ymdhmsM]+", "");
		return new SimpleDateFormat(result).format(new Date());
	}

	public void initializeComponents() {
		initializeFrame();
		initializeColorPicker();
		initializeDirectory();
		initializeCheckboxes();
		initializeDescriptions();
		initializeSearch();
		initializeKeyCheckers("[ 영역 지정 캡쳐 ]", Options.CAP_SEL, 10, 30, kd[0]);
		initializeKeyCheckers("[ 전체 영역 캡처 ]", Options.CAP_ALL, 10, 130, kd[1]);
		initializeKeyCheckers("[ 영역 지정 ]", Options.SIZE, 10, 230, kd[2]);
		SwingUtilities.invokeLater(() -> {
			setVisible(true);
			setLookAndFeel(new NimbusLookAndFeel(), this);
		});
	}

	public void initializeFrame() {
		setTitle("옵션 설정");
		setSize(700, 450);
		setResizable(false);
		setLayout(null);
	}

	public void initializeColorPicker() {
		color.setSize(200, 200);
		add(color, 470, 70);
	}

	public void initializeDirectory() {
		JLabel visible = new JLabel("저장 경로:   ");
		visible.setSize(80, 30);
		add(visible, 170, 30);
		dir.setSize(140, 30);
		add(dir, 250, 30);
		dir.setText(sof.getValue(Options.DIR));
		dir.addTypeListener((e) -> sof.setValue(Options.DIR, dir.getText()));
		dir.addPressListener((e) -> sof.setValue(Options.DIR, dir.getText()));
	}

	public void initializeCheckboxes() {
		save.setSize(130, 15);
		save.setSelected(sof.getValue(Options.SAVE).contains("t"));
		add(save, 170, 290);
		img.setSize(130, 15);
		img.setSelected(sof.getValue(Options.VIEWER).contains("t"));
		add(img, 170, 310);
		img.addActionListener((a) -> sof.setValue(Options.VIEWER,
				"" + img.isSelected()));
		save.addActionListener((a) -> sof.setValue(Options.SAVE,
				"" + save.isSelected()));
	}

	public void setLookAndFeel(LookAndFeel laf, Component comp) {
		try {
			UIManager.setLookAndFeel(laf);
			SwingUtilities.updateComponentTreeUI(comp);
			comp.repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initializeKeyCheckers(String label, String key, int x, int y,
			KeyData kd) {
		JLabel l = new JLabel(label);
		l.setFont(DEFAULT_FONT);
		l.setSize(130, 50);

		JLabel l2 = new JLabel("입력 키: ");
		l2.setSize(50, 30);

		JCheckBox[] checkBoxes = new JCheckBox[3];
		WrappedTextField keyField = new WrappedTextField();
		keyField.setSize(50, 30);
		keyField.addTypeListener((e) -> {
			String s = check(keyField, e);
			SwingUtilities.invokeLater(() -> {
				keyField.setText(s);
				sof.setValue(key, ser(keyField, checkBoxes));
				kd.setKeyChar(s.charAt(0));
			});
		});
		checkBoxes[0] = new JCheckBox("Alt");
		checkBoxes[0].setSize(45, 30);
		checkBoxes[1] = new JCheckBox("Shift");
		checkBoxes[1].setSize(55, 30);
		checkBoxes[2] = new JCheckBox("Ctrl");
		checkBoxes[2].setSize(55, 30);
		checkBoxes[0].addActionListener((a) -> {
			sof.setValue(key, ser(keyField, checkBoxes));
			kd.setAltRequired(checkBoxes[0].isSelected());
		});
		checkBoxes[1].addActionListener((a) -> {
			sof.setValue(key, ser(keyField, checkBoxes));
			kd.setShiftRequired(checkBoxes[1].isSelected());
		});
		checkBoxes[2].addActionListener((a) -> {
			sof.setValue(key, ser(keyField, checkBoxes));
			kd.setControlRequired(checkBoxes[2].isSelected());
		});

		add(l, x, y);
		add(l2, x, y + 70);
		add(keyField, x + 50, y + 70);

		add(checkBoxes[0], x, y + 40);
		add(checkBoxes[1], x + 45, y + 40);
		add(checkBoxes[2], x + 100, y + 40);

		Result<String> k = Result.create();
		Result<Boolean>[] out = new Result[3];
		Result.fill(out);
		deser(sof.getValue(key), out[0], out[1], out[2], k);

		keyField.setText(k.get());
		checkBoxes[0].setSelected(out[0].get());
		checkBoxes[1].setSelected(out[1].get());
		checkBoxes[2].setSelected(out[2].get());
		kd.setKeyChar(k.get().charAt(0));
		kd.setAltRequired(checkBoxes[0].isSelected());
		kd.setShiftRequired(checkBoxes[1].isSelected());
		kd.setControlRequired(checkBoxes[2].isSelected());
	}

	String ser(WrappedTextField key, JCheckBox a[]) {
		return key.getText() + ", " + (a[0].isSelected() ? "A" : "")
				+ (a[1].isSelected() ? "S" : "")
				+ (a[2].isSelected() ? "C" : "");
	}

	void deser(String text, Result<Boolean> alt, Result<Boolean> shift,
			Result<Boolean> control, Result<String> key) {
		String[] texts = text.split(",");
		key.set(texts[0]);
		if (texts.length > 1) {
			alt.set(texts[1].contains("A"));
			shift.set(texts[1].contains("S"));
			control.set(texts[1].contains("C"));
		} else {
			alt.set(false);
			shift.set(false);
			control.set(false);
		}
	}

	public void initializeDescriptions() {
		desc.setSize(250, 180);
		desc.setEditable(false);
		desc.setFocusable(false);
		desc.setDragEnabled(false);
		add(desc, 170, 110);
	}

	public void initializeSearch() {
		JLabel visible = new JLabel("사진 이름:   ");
		visible.setSize(80, 30);
		add(visible, 170, 70);

		fn.setSize(140, 30);
		fn.setText(sof.getValue(Options.FILE));
		fn.addTypeListener((e) -> SwingUtilities.invokeLater(() -> {
			updateDescription();
			sof.setValue(Options.FILE, fn.getText());
		}));
		add(fn, 250, 70);

		search.setSize(120, 30);
		add(search, 400, 30);
		search.addActionListener(a -> {
			String path = new WrappedFileChooser()
					.select(dir.getText() + "폴더 선택을 폴더 안에서 눌러주세요.")
					.title("스크린샷 저장 경로 탐색...").filter(FolderFilter.getFilter())
					.visible(this, "폴더 선택").getSelectedFile().getParent();
			dir.setText(path);
			sof.setValue(Options.DIR, getDir());
		});
	}

	private OptionFrame() {
		updateDescription();
		initializeComponents();
	}

	protected String check(WrappedTextField tf, KeyEvent e) {
		if (e.isShiftDown() || e.isAltDown() || e.isControlDown())
			return tf.getText();
		return (e.getKeyChar() + "").toUpperCase();
	}

	public void add(Component comp, int x, int y) {
		comp.setBounds(x, y, comp.getWidth(), comp.getHeight());
		add(comp);
	}

	public static boolean save() {
		return save.isSelected();
	}

	public static boolean image() {
		return img.isSelected();
	}

	public static Color getColor() {
		return color.getColor();
	}

	public static KeyData getSelectCapture() {
		return kd[0];
	}

	public static KeyData getAllCapture() {
		return kd[1];
	}

	public static KeyData getSizeSell() {
		return kd[2];
	}
}
