package com.hangum.db.browser.rap.core.actions.global;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import com.hangum.db.browser.rap.core.Activator;
import com.hangum.db.browser.rap.core.Messages;
import com.hangum.db.browser.rap.core.actions.erd.ERDViewAction;
import com.hangum.db.dao.system.UserDBDAO;
import com.swtdesigner.ResourceManager;

/**
 * 현재 선택된 디비의 릴레이션 화면을 오픈합니다.
 * 
 * @author hangum
 *
 */
public class OpenDBRelationAction extends Action implements ISelectionListener, IWorkbenchAction {
	private final IWorkbenchWindow window;
	private final static String ID = "com.hangum.db.browser.rap.core.actions.global.DBRelationOpenAction"; //$NON-NLS-1$
	private IStructuredSelection iss;
	
	public OpenDBRelationAction(IWorkbenchWindow window) {
		this.window = window;
		
		setId(ID);
		setText(Messages.OpenDBRelationAction_1);
		setToolTipText(Messages.OpenDBRelationAction_2);
		setImageDescriptor( ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, "resources/icons/erd.png"));
		setEnabled(false);
		
		window.getSelectionService().addPostSelectionListener(this);
	}
	
	@Override
	public void run() {
		UserDBDAO userDB = (UserDBDAO)iss.getFirstElement();
		
		ERDViewAction qea = new ERDViewAction();
		qea.run(userDB);
	}
	

	@Override
	public void dispose() {
		window.getSelectionService().removePostSelectionListener(this);
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		IStructuredSelection sel = (IStructuredSelection)selection;
		if(sel != null) {
			if( sel.getFirstElement() instanceof UserDBDAO ) {
				iss = sel;
				
				setEnabled(true);
			} else setEnabled(false);
		} else setEnabled(false);
	}

}
