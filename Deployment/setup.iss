; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "ERP"
#define MyAppVersion "1.2"
#define MyAppPublisher "Group Number 4"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{59BF0AE1-DC73-4ACB-9E54-AABA6DA92DF4}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
DefaultDirName={pf}\{#MyAppName}
DefaultGroupName={#MyAppName}
OutputBaseFilename=setup_latest
SetupIconFile=C:\Users\ppash\Dropbox\[UNIVERSITY]\Sem8\OOD\Project\Deliverables\Deployment\applications_system.ico
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "C:\Users\ppash\Dropbox\[UNIVERSITY]\Sem8\OOD\Project\Deliverables\Deployment\backup.php"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\ppash\Dropbox\[UNIVERSITY]\Sem8\OOD\Project\Deliverables\Deployment\erp.conf"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\ppash\Dropbox\[UNIVERSITY]\Sem8\OOD\Project\Deliverables\Deployment\ERP.jar"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\ppash\Dropbox\[UNIVERSITY]\Sem8\OOD\Project\Deliverables\Deployment\erp_db.sql"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\ppash\Dropbox\[UNIVERSITY]\Sem8\OOD\Project\Deliverables\Deployment\install_db.php"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\ppash\Dropbox\[UNIVERSITY]\Sem8\OOD\Project\Deliverables\Deployment\restore.php"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\ppash\Dropbox\[UNIVERSITY]\Sem8\OOD\Project\Deliverables\Deployment\lib\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

