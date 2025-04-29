console.log("export");

function exportdata() {
  const rows = Array.from(document.querySelectorAll("#contact-table tbody tr"));

  const exportData = [["Name", "Email", "Phone", "Website", "LinkedIn"]];

  rows.forEach(row => {
    const name = row.querySelector(".export-name")?.textContent.trim() || "";
    const email = row.querySelector(".export-email")?.textContent.trim() || "";
    const phone = row.querySelector("td:nth-child(2) span")?.textContent.trim() || "";
    const website = row.querySelector(".export-website")?.textContent.trim() || "";
    const linkedin = row.querySelector(".export-linkedin")?.textContent.trim() || "";

    exportData.push([name, email, phone, website, linkedin]);
  });

  const table = document.createElement("table");
  exportData.forEach(rowData => {
    const row = table.insertRow();
    rowData.forEach(cellData => {
      const cell = row.insertCell();
      cell.textContent = cellData;
    });
  });

  TableToExcel.convert(table, {
    name: "contacts.xlsx",
    sheet: {
      name: "Contacts"
    }
  });
}
